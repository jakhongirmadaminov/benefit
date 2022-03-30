package uz.magnumactive.benefit.ui.expenses_by_categories


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.PartnersRemoteImpl
import uz.magnumactive.benefit.remote.models.PartnerCategoryDTO
import uz.magnumactive.benefit.remote.models.TransactionAnalyticsContainerDTO
import uz.magnumactive.benefit.remote.models.TransactionAnalyticsDTO
import uz.magnumactive.benefit.remote.models.TransactionInOutDTO
import uz.magnumactive.benefit.util.*
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
        analyticsReportLoading.postValue(true)
        viewModelScope.launch(IO) {
            val thisMonthsStartDateTime = DateTime.now().dayOfMonth().withMinimumValue()
            val monthlyAnalyticsReportMap =
                arrayListOf<Deferred<ResultWrapper<TransactionInOutDTO>>>()

            for (i in 0..11) {
                monthlyAnalyticsReportMap.add(async {
                    getFormattedResponse(totalExpenseReportLoading) {
                        apiService.transactionsInOut(
                            cardId,
                            DateTimeFormat.forPattern("yyyyMMdd").print(
                                DateTime.now().withMonthOfYear(i + 1).dayOfMonth()
                                    .withMinimumValue()
                            ).toInt(),
                            DateTimeFormat.forPattern("yyyyMMdd").print(
                                DateTime.now().withMonthOfYear(i + 1).dayOfMonth()
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

            val monthlyAnalyticsMap =
                arrayListOf<Deferred<ResultWrapper<List<TransactionAnalyticsContainerDTO>>>>()

            for (i in 0..11) {
                monthlyAnalyticsMap.add(async {
                    getFormattedResponse(analyticsReportLoading) {
                        apiService.transactionsAnalytics(
                            cardId,
                            DateTimeFormat.forPattern("yyyyMMdd").print(
                                DateTime.now().withMonthOfYear(i + 1).dayOfMonth()
                                    .withMinimumValue()
                            ).toInt(),
                            DateTimeFormat.forPattern("yyyyMMdd").print(
                                DateTime.now().withMonthOfYear(i + 1).dayOfMonth()
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

            analyticsReportLoading.postValue(false)
            transactionsAnalyticsResp.postValue(ResultSuccess(monthlyTransactions.map {
                (it as ResultSuccess).value.last().content
            }))
            transactionsReportResp.postValue(ResultSuccess(monthlyTransactionsReport.map {
                (it as ResultSuccess).value
            }))
        }
    }

}