package com.example.benefit.ui.main.payments_and_transfers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.AutoPaymentDTO
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PaymentsAndTransfersViewModel @Inject constructor(private val userRemote: UserRemote, private val authApi: AuthApiService) : ViewModel() {

    val cardsResp = MutableLiveData<List<CardDTO>>()
    val isLoadingCards = MutableLiveData<Boolean>()
    val signInRequired = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getMyCards() {
        isLoadingCards.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.getMyCards()
            withContext(Dispatchers.Main) {
                isLoadingCards.value = false
                when (response) {
                    is ResultError -> {
                        if (response.code == 403) signInRequired.value = true
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        cardsResp.value = response.value.getProperly()
                    }
                }.exhaustive
            }
        }
    }

    val autoPaymentsReqState = MutableLiveData<RequestState<List<AutoPaymentDTO>>>()
    fun getMyAutoPayments() {
        viewModelScope.launch(IO) {
            makeRequest(autoPaymentsReqState) { authApi.getMyAutoPayments() }
        }
    }
}