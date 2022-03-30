package uz.magnumactive.benefit.ui.main.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.CardDTO
import uz.magnumactive.benefit.remote.models.PaynetCategory
import uz.magnumactive.benefit.remote.repository.UserRemote
import uz.magnumactive.benefit.stories.data.Story
import uz.magnumactive.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRemote: UserRemote,
    private val apiAuth: AuthApiService
) : ViewModel() {

    val paynetCatgResp = MutableLiveData<List<PaynetCategory>>()
    val errorMessage = MutableLiveData<String>()
    val isLoadingPaynetCategories = MutableLiveData<Boolean>()

    fun getPaynetCategories() {
        isLoadingPaynetCategories.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.paymentCategories()
            withContext(Dispatchers.Main) {
                isLoadingPaynetCategories.value = false
                when (response) {
                    is ResultError -> errorMessage.value = response.message
                    is ResultSuccess -> paynetCatgResp.value = response.value
                }.exhaustive
            }
        }
    }

    val storiesResp = MutableLiveData<ResultWrapper<List<Story>>>()
    val isLoadingStories = MutableLiveData<Boolean>()
    fun getStories() {
        isLoadingStories.value = true
        viewModelScope.launch(Dispatchers.IO) {
            storiesResp.postValue(getFormattedResponse(isLoadingStories) { apiAuth.getStories() })
        }
    }

    var supremeCard = MutableLiveData<CardDTO?>(null)
    val cardsResp = MutableLiveData<List<CardDTO>>()
    val isLoadingCards = MutableLiveData<Boolean>()
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
                        supremeCard.postValue(cardsResp.value?.find { it.isSupreme() })
                    }
                }.exhaustive
            }
        }
    }

    fun getBftBalance() = flow {
        emit(apiAuth.getBftInfo().result?.data?.balanceInfo?.summa)
    }.catch {
        emit(null)
    }


}