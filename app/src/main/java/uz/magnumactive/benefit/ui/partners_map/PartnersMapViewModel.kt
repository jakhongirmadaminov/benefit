package uz.magnumactive.benefit.ui.partners_map


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.models.Partner
import uz.magnumactive.benefit.remote.repository.PartnersRemote
import uz.magnumactive.benefit.util.ResultError
import uz.magnumactive.benefit.util.ResultSuccess
import uz.magnumactive.benefit.util.SingleLiveEvent
import uz.magnumactive.benefit.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PartnersMapViewModel @Inject constructor(private val partnersRepository: PartnersRemote) :
    ViewModel() {

    var errorMessage = SingleLiveEvent<String?>()
    var isLoading = SingleLiveEvent<Boolean>()
    val partnersResp = SingleLiveEvent<List<Partner>>()

    fun getPartnersForCategory(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRepository.getPartnersByCategoryId(id)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        partnersResp.value = response.value
                    }
                }
            }.exhaustive
        }
    }
}


