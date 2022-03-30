package uz.magnumactive.benefit.ui.payments


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.BalanceInfo
import uz.magnumactive.benefit.remote.models.BftInfoDTO
import uz.magnumactive.benefit.remote.models.MyCardsResp
import uz.magnumactive.benefit.remote.models.PaynetPaymentResponse
import uz.magnumactive.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaynetTransactionViewModel @Inject constructor(private val apiAuth: AuthApiService) :
    ViewModel() {

    val isLoading = MutableLiveData(false)
    val error = MutableLiveData<String>()

    val bftAndMyCardsPair = MutableLiveData<Pair<BalanceInfo, MyCardsResp>>()

    fun getMyCards() {
        viewModelScope.launch(Dispatchers.IO) {
            val cardsResp = async { getFormattedResponse { apiAuth.getMyCards() } }
            val bftResp = async { getFormattedResponse { apiAuth.getBftInfo() } }

            processResponses(cardsResp.await(), bftResp.await())
        }
    }

    private fun processResponses(
        cardsResp: ResultWrapper<MyCardsResp>,
        bftResp: ResultWrapper<BftInfoDTO>
    ) {
        if (cardsResp is ResultSuccess && bftResp is ResultSuccess) {
            bftAndMyCardsPair.postValue(Pair(bftResp.value.balanceInfo!!, cardsResp.value))
        } else {
            error.postValue(
                (cardsResp as? ResultError)?.message ?: (bftResp as? ResultError)?.message
            )
        }
    }


    val transactionState = MutableLiveData<RequestState<PaynetPaymentResponse>>()
    fun pay(
        serviceId: Long,
        providerId: Long,
        fields: String,
        summa: Int,
        cardId: Long,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(transactionState) {
                apiAuth.paynetPayCard(
                    serviceId,
                    providerId,
                    fields,
                    summa,
                    cardId,
                )
            }
        }
    }

    fun payWithCashback(serviceId: Long, providerId: Long, fields: String, summa: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(transactionState) {
                apiAuth.payWithCashback(
                    serviceId,
                    providerId,
                    fields,
                    summa,
                )
            }
        }
    }

}