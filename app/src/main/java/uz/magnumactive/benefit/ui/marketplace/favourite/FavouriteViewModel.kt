package uz.magnumactive.benefit.ui.marketplace.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.MarketFavouriteProductDTO
import uz.magnumactive.benefit.ui.marketplace.cart.BaseBasketViewModel
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val authClient: AuthApiService) :
    BaseBasketViewModel(authClient) {

    val favouritesResult = MutableLiveData<RequestState<List<MarketFavouriteProductDTO>>>()

    fun getFavourites() {
        viewModelScope.launch {
            makeRequest(favouritesResult) { authClient.getFavourites() }
        }
    }


    val removeFromFavResp = MutableLiveData<RequestState<Any>>()

    fun removeFromFav(id: Long) {
        viewModelScope.launch {
            makeRequest(removeFromFavResp) { authClient.removeFromFavourites(id) }
        }
    }


    val removeAllFromFavResp = MutableLiveData<RequestState<Any>>()
    fun removeAllFromFav() {
        viewModelScope.launch {
            makeRequest(removeAllFromFavResp) { authClient.removeAllFromFavourites() }
        }
    }


}