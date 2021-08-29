package com.example.benefit.ui.main.fill_card


/**
 * Created by jahon on 03-Sep-20
 */
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.RespPid2Pid
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

    val p2pId2IdLoading = MutableLiveData<Boolean>()
    val p2pId2IdResp = MutableLiveData<ResultWrapper<RespPid2Pid>>()

    fun p2pId2Id(amount: Int, fromId: String, toId: String) {
        viewModelScope.launch {
            p2pId2IdResp.postValue(
                getFormattedResponse(p2pId2IdLoading) {
                    authApiService.p2pIdToId(
                        amount,
                        fromId,
                        toId
                    )
                })
        }
    }


    val p2pPan2IdLoading = MutableLiveData<Boolean>()
    val p2pPan2IdResp = MutableLiveData<ResultWrapper<RespPid2Pid>>()

    fun p2pPan2Id(amount: Int, cardId: Int, pan: String, expiry: String) {
        viewModelScope.launch {
            p2pPan2IdResp.postValue(
                getFormattedResponse(p2pPan2IdLoading) {
                    authApiService.p2pPanToId(
                        amount,
                        cardId,
                        pan,
                        expiry
                    )
                })
        }
    }


}