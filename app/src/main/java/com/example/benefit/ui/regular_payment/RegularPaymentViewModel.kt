package com.example.benefit.ui.regular_payment


/**
 * Created by jahon on 03-Sep-20
 */
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.*
import com.example.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegularPaymentViewModel @Inject constructor(private val apiAuth: AuthApiService) :
    ViewModel() {

    val bftAndMyCardsPair = MutableLiveData<Pair<BalanceInfo, MyCardsResp>>()
    val error = MutableLiveData<String>()
    val isGettingsCards = MutableLiveData(false)

    init {
        getMyCards()
    }

    fun getMyCards() {
        viewModelScope.launch(Dispatchers.IO) {
            isGettingsCards.postValue(true)
            val cardsResp = async { getFormattedResponse { apiAuth.getMyCards() } }
            val bftResp = async { getFormattedResponse { apiAuth.getBftInfo() } }

            processResponses(cardsResp.await(), bftResp.await())
        }
    }

    private fun processResponses(
        cardsResp: ResultWrapper<MyCardsResp>,
        bftResp: ResultWrapper<BftInfoDTO>
    ) {
        isGettingsCards.postValue(false)
        if (cardsResp is ResultSuccess && bftResp is ResultSuccess) {
            bftAndMyCardsPair.postValue(Pair(bftResp.value.balanceInfo!!, cardsResp.value))
        } else {
            error.postValue(
                (cardsResp as? ResultError)?.message ?: (bftResp as? ResultError)?.message
            )
        }
    }

    val transactionState = MutableLiveData<RequestState<PaynetPaymentResponse>>()
    fun makePayment(autoPaymentDTO: AutoPaymentDTO, cardId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(transactionState) {
                apiAuth.paynetPayCard(
                    autoPaymentDTO.serviceInfo!!.ownId!!,
                    autoPaymentDTO.providerInfo!!.own_id!!,
                    autoPaymentDTO.fields!!,
                    autoPaymentDTO.amount!!,
                    cardId,
                )
            }
        }
    }


}