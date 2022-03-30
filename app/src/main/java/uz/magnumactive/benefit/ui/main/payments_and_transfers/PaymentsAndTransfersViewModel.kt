package uz.magnumactive.benefit.ui.main.payments_and_transfers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.AutoPaymentDTO
import uz.magnumactive.benefit.remote.models.CardDTO
import uz.magnumactive.benefit.remote.repository.UserRemote
import uz.magnumactive.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PaymentsAndTransfersViewModel @Inject constructor(
    private val userRemote: UserRemote,
    private val authApi: AuthApiService
) : ViewModel() {

    val cardsResp = MutableLiveData<List<CardDTO>>()
    val isLoadingCards = MutableLiveData<Boolean>()
    val signInRequired = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getMyCards() {
        isLoadingCards.value = true
        viewModelScope.launch(IO) {
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