package com.example.benefit.ui.main.transfer_to_card


/**
 * Created by jahon on 03-Sep-20
 */
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.UserRemoteImpl
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.CardP2PDTO
import com.example.benefit.remote.models.RespPid2Pid
import com.example.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransferToCardViewModel @Inject constructor(
    private val userRemote: UserRemoteImpl,
    val authApi: AuthApiService
) :
    ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val cardsResp = MutableLiveData<List<CardDTO>>()
    val isLoadingCards = MutableLiveData<Boolean>()

    fun getMyCards() {
        isLoadingCards.value = true
        viewModelScope.launch(IO) {
            val response = userRemote.getMyCards()
            withContext(Dispatchers.Main) {
                isLoadingCards.value = false
                when (response) {
                    is ResultError -> errorMessage.value = response.message
                    is ResultSuccess -> cardsResp.value = response.value.getProperly()
                }.exhaustive
            }
        }
    }

    val panInfoLoading = MutableLiveData<Boolean>()
    val panResp = MutableLiveData<ResultWrapper<CardP2PDTO>>()

    fun getCardP2PInfo(pan: String) {
        viewModelScope.launch(IO) {
            panResp.postValue(getFormattedResponse(panInfoLoading) { authApi.getP2PCardInfo(pan) })
        }
    }

    val transactionLoading = MutableLiveData<Boolean>()
    val transactionResp = MutableLiveData<ResultWrapper<RespPid2Pid>>()

    fun transferToCard(selectedcardId: Long, cardP2pTarget: CardP2PDTO, amount: Int) {
        viewModelScope.launch(IO) {
            transactionResp.postValue(getFormattedResponse(transactionLoading) {
                authApi.p2pIdToPan(
                    amount,
                    selectedcardId,
                    cardP2pTarget.pan!!
                )
            })
        }
    }


}