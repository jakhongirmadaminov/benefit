package com.example.benefit.ui.transactions_history


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.TransactionAnalyticsContainerDTO
import com.example.benefit.remote.models.TransactionAnalyticsDTO
import com.example.benefit.remote.models.TransactionInOutDTO
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.getFormattedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

@HiltViewModel
class TransactionsHistoryViewModel @Inject constructor(val apiService: AuthApiService) :
    ViewModel() {


    val totalExpenseReportLoading = MutableLiveData<Boolean>()
    val analyticsReportLoading = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val transactionsReportResp = MutableLiveData<ResultWrapper<List<TransactionInOutDTO>>>()
    val transactionsAnalyticsResp =
        MutableLiveData<ResultWrapper<ArrayList<ArrayList<TransactionAnalyticsDTO>>>>()


    fun getTransactionsHistory(cardId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
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

            val monthlyAnalyticsMap =
                arrayListOf<Deferred<ResultWrapper<ArrayList<TransactionAnalyticsContainerDTO>>>>()

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

            val monthlyTransactionsReport = monthlyAnalyticsReportMap.awaitAll()
            val monthlyTransactions = monthlyAnalyticsMap.awaitAll()

            monthlyTransactions.forEach {
                if (it !is ResultSuccess) {
                    transactionsAnalyticsResp.postValue(ResultError())
                    return@launch
                }
            }
            monthlyTransactionsReport.forEach {
                if (it !is ResultSuccess) {
                    transactionsReportResp.postValue(ResultError())
                    return@launch
                }
            }

            transactionsAnalyticsResp.postValue(ResultSuccess(ArrayList( monthlyTransactions.map {
                (it as ResultSuccess).value.last().content
            })  ))
            transactionsReportResp.postValue(ResultSuccess(monthlyTransactionsReport.map {
                (it as ResultSuccess).value
            }))
            isLoading.postValue(false)

        }
    }


}