package uz.magnumactive.benefit.ui.marketplace.dialogs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.Partner
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
class MarketProductDetailsViewModel @Inject constructor(private val authClient: AuthApiService) :
    ViewModel() {


    val details = MutableLiveData<RequestState<Partner>>()

    fun getProductDetails(id: Long) {
        viewModelScope.launch {
            makeRequest(details) { authClient.getProductDetails(id) }
        }
    }
}