package com.example.benefit.ui.expenses_by_categories


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.PartnersRemoteImpl
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.remote.models.TransactionAnalyticsContainerDTO
import com.example.benefit.remote.models.TransactionAnalyticsDTO
import com.example.benefit.remote.models.TransactionInOutDTO
import com.example.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

@HiltViewModel
class ExpensesByCategoriesViewModel @Inject constructor(
    val partnersRemoteImpl: PartnersRemoteImpl,
    val apiService: AuthApiService
) : ViewModel() {


    val partnerCategoriesResp = SingleLiveEvent<ResultWrapper<List<PartnerCategoryDTO>>>()


    fun getPartnerCategories() {
        viewModelScope.launch(IO) {
            val response = partnersRemoteImpl.getPartnersCategory()
            withContext(Dispatchers.Main) {
                partnerCategoriesResp.value = response
            }
        }
    }

    val totalExpenseReportLoading = MutableLiveData<Boolean>()
    val analyticsReportLoading = MutableLiveData<Boolean>()
    val transactionsReportResp = MutableLiveData<ResultWrapper<List<TransactionInOutDTO>>>()
    val transactionsAnalyticsResp =
        MutableLiveData<ResultWrapper<List<List<TransactionAnalyticsDTO>>>>()


    fun getExpensesAndAnalytics(cardId: Long) {
        viewModelScope.launch(IO) {
            val thisMonthsStartDateTime = DateTime.now().dayOfMonth().withMinimumValue()

            val monthlyAnalyticsReportMap =
                arrayListOf<Deferred<ResultWrapper<TransactionInOutDTO>>>()

            for (i in 0..11) {
                monthlyAnalyticsReportMap.add(async {
                    getFormattedResponse(totalExpenseReportLoading) {
                        apiService.transactionsInOut(
                            cardId,
                            DateTimeFormat.forPattern("yyyy0101")
                                .print(thisMonthsStartDateTime.minusMonths(i)).toInt(),
                            DateTimeFormat.forPattern("yyyy1231")
                                .print(
                                    thisMonthsStartDateTime.minusMonths(i).dayOfMonth()
                                        .withMaximumValue()
                                ).toInt()
                        )
                    }
                })
            }


            val monthlyTransactionsReport = monthlyAnalyticsReportMap.awaitAll()

            monthlyTransactionsReport.forEach {
                if (it !is ResultSuccess) {
                    transactionsReportResp.postValue(ResultError())
                    return@launch
                }
            }

            transactionsReportResp.postValue(ResultSuccess(monthlyTransactionsReport.map {
                (it as ResultSuccess).value
            }))

            val monthlyAnalyticsMap =
                arrayListOf<Deferred<ResultWrapper<List<TransactionAnalyticsContainerDTO>>>>()

            for (i in 0..11) {
                monthlyAnalyticsMap.add(async {
                    getFormattedResponse(analyticsReportLoading) {
                        apiService.transactionsAnalytics(
                            cardId,
                            DateTimeFormat.forPattern("yyyy0101")
                                .print(thisMonthsStartDateTime.minusMonths(i)).toInt(),
                            DateTimeFormat.forPattern("yyyy1231")
                                .print(
                                    thisMonthsStartDateTime.minusMonths(i).dayOfMonth()
                                        .withMaximumValue()
                                ).toInt()
                        )
                    }
                })
            }

            val monthlyTransactions = monthlyAnalyticsMap.awaitAll()

            monthlyTransactions.forEach {
                if (it !is ResultSuccess) {
                    transactionsAnalyticsResp.postValue(ResultError())
                    return@launch
                }
            }

            transactionsAnalyticsResp.postValue(ResultSuccess(monthlyTransactions.map {
                (it as ResultSuccess).value.last().content
            }))
        }
    }

}