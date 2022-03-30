package uz.magnumactive.benefit.ui.payments


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.PaynetService
import uz.magnumactive.benefit.remote.models.ServiceField
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillMerchantFieldsViewModel @Inject constructor(
    private val apiAuth: AuthApiService
) : ViewModel() {

    //    var supremeCard: CardDTO? = null
    val paynetServices = MutableLiveData<RequestState<List<PaynetService>>>()
    val isLoadingCards = MutableLiveData<Boolean>()
    fun getPaynetServicesForProviderId(id: Long) {
        isLoadingCards.value = true
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(paynetServices) { apiAuth.getPaynetServices(id) }
        }
    }

    val payState = MutableLiveData<RequestState<Any>>()
    fun payCheck(serviceId: Long, providerId: Long) {
        (paynetServices.value as? RequestState.Success)?.value?.let { services ->

            viewModelScope.launch(Dispatchers.IO) {
                val fields = StringBuilder()
                services[0].service_fields?.forEach {
                    fields.append("\"${it.name}\":")
                    fields.append("\"${it.userSelection}\",")
                }

                makeRequest(payState) {
                    apiAuth.paynetPayCheck(
                        serviceId,
                        providerId,
                        fields.removeSuffix(",").toString()
                    )
                }

            }
        }
    }

    fun areAllFieldsSelected(service: PaynetService): Boolean {
        fields.forEachIndexed { index, fieldValue ->
            if (service.service_fields!![index].required == 1 && fieldValue.userSelection.isNullOrBlank()) {
                return false
            }
        }
        return true
    }

    val fields = arrayListOf<ServiceField>()


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