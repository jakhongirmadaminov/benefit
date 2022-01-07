package com.example.benefit.ui.main.payments_and_transfers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PaymentsAndTransfersViewModel @Inject constructor(private val userRemote: UserRemote) : ViewModel() {

    val cardsResp = MutableLiveData<List<CardDTO>>()
    val isLoadingCards = MutableLiveData<Boolean>()
    val signInRequired = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    init {
        getMyCards()
    }

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
}