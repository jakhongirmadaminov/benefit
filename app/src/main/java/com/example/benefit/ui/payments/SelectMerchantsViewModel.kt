package com.example.benefit.ui.payments


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.PaynetCategory
import com.example.benefit.remote.models.PaynetMerchant
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import com.example.benefit.util.getParsedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SelectMerchantsViewModel @Inject constructor(
    private val apiAuth: AuthApiService
) : ViewModel() {

    //    var supremeCard: CardDTO? = null
    val paynetMerchants = MutableLiveData<RequestState<List<PaynetMerchant>>>()
    val isLoadingCards = MutableLiveData<Boolean>()
    fun getProvidersForPaynetCategoryId(id: Long) {
        isLoadingCards.value = true
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(paynetMerchants) {apiAuth.getPaynetProviders(id)  }
        }
    }


//    var supremeCard: CardDTO? = null
//    val cardsResp = MutableLiveData<List<CardDTO>>()
//    val isLoadingCards = MutableLiveData<Boolean>()
//    val signInRequired = MutableLiveData<Boolean>()
//    fun getMyCards() {
//        isLoadingCards.value = true
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = userRemote.getMyCards()
//            withContext(Dispatchers.Main) {
//                isLoadingCards.value = false
//                when (response) {
//                    is ResultError -> {
//                        if (response.code == 403) signInRequired.value = true
//                        errorMessage.value = response.message
//                    }
//                    is ResultSuccess -> {
//                        cardsResp.value = response.value.getProperly()
//                        supremeCard = cardsResp.value?.find { it.isSupreme() }
//                    }
//                }.exhaustive
//            }
//        }
//    }


}