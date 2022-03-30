package uz.magnumactive.benefit.ui.main.home.card_options


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.models.CardBgDTO
import uz.magnumactive.benefit.remote.repository.CardsRemote
import uz.magnumactive.benefit.util.ResultError
import uz.magnumactive.benefit.util.ResultSuccess
import uz.magnumactive.benefit.util.SingleLiveEvent
import uz.magnumactive.benefit.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jahon on 03-Sep-20
 */import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CardOptionsViewModel @Inject constructor(private val cardsRemote: CardsRemote) :
    ViewModel() {

    val isLoading = SingleLiveEvent<Boolean>()
    val errorMessage = SingleLiveEvent<String?>()
    val deleteCardResp = SingleLiveEvent<Boolean>()
    val cardBgs = SingleLiveEvent<List<CardBgDTO>>()
    val setCardDesignResp = SingleLiveEvent<String>()
    val blockCardResp = SingleLiveEvent<Boolean>()

    fun deleteCard(cardId: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = cardsRemote.deleteCard(cardId)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        deleteCardResp.value = true
                    }
                }.exhaustive
            }
        }
    }

    fun blockCard(cardId: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = cardsRemote.blockCard(cardId)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        blockCardResp.value = true
                    }
                }.exhaustive
            }
        }
    }


    fun getCardDesigns() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = cardsRemote.getCardBackgrounds()
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        cardBgs.value = response.value
                    }
                }.exhaustive
            }
        }
    }


    fun setCardDesign(bgId: Int, cardId: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = cardsRemote.changeCardDesign(bgId, cardId)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        setCardDesignResp.value = ""
                    }
                }.exhaustive
            }
        }
    }

}