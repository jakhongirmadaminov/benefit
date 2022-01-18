package com.example.benefit.ui.payments


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.MyCardsResp
import com.example.benefit.util.RequestState
import com.example.benefit.util.makeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaynetTransactionViewModel @Inject constructor(private val apiAuth: AuthApiService) :
    ViewModel() {

    val myCards = MutableLiveData<RequestState<MyCardsResp>>()
    fun getMyCards() {
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(myCards) { apiAuth.getMyCards() }
        }
    }

    val transactionState = MutableLiveData<RequestState<Any>>()
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

}