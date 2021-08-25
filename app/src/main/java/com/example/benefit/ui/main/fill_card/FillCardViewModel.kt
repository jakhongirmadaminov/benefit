package com.example.benefit.ui.main.fill_card


/**
 * Created by jahon on 03-Sep-20
 */
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.PlainResp
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FillCardViewModel @Inject constructor(
    private val userRemote: UserRemote,
    private val authApiService: AuthApiService
) :
    ViewModel() {

    val cardsResp = MutableLiveData<List<CardDTO>>()
    val isLoadingCards = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val signInRequired = MutableLiveData<Boolean>()
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

    val p2pLoading = MutableLiveData<Boolean>()
    val p2pResp = MutableLiveData<ResultWrapper<PlainResp>>()

    fun p2pId2Id(amount: Int, fromId: String, toId: String) {
        viewModelScope.launch {
            p2pResp.postValue(
                getFormattedResponse(p2pLoading) { authApiService.p2pIdToId(amount, fromId, toId) })
        }
    }


}