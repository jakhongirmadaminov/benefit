package uz.magnumactive.benefit.ui.marketplace.dialogs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.MarketProductDetailsDTO
import uz.magnumactive.benefit.ui.marketplace.BaseBasketViewModel
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
class MarketProductDetailsViewModel @Inject constructor(private val authClient: AuthApiService) :
    BaseBasketViewModel(authClient) {


    val details = MutableLiveData<RequestState<MarketProductDetailsDTO>>()
    val addToFavResult = MutableLiveData<RequestState<Any>>()

    val count = MutableLiveData(1)

    fun getProductDetails(id: Long) {
        viewModelScope.launch {
            makeRequest(details) { authClient.getProductDetails(id) }
        }
    }

    fun addToFavourites(id: Long) {
        viewModelScope.launch {
            makeRequest(addToFavResult) { authClient.addToFavourites(id) }
        }
    }

    fun removeFromFavorites(id: Long) {
        viewModelScope.launch {
            makeRequest(addToFavResult) { authClient.removeFromFavourites(id) }
        }
    }
}
