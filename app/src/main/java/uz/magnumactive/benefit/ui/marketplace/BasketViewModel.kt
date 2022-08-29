package uz.magnumactive.benefit.ui.marketplace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest

open class BasketViewModel(val api: AuthApiService) : ViewModel() {

     val myCart = MutableLiveData<RequestState<List<MarketProductDTO>>>()
    fun getMyCart() {
        viewModelScope.launch { makeRequest(myCart) { api.getBasketList() } }
    }

     val addToCartResp = MutableLiveData<RequestState<Any>>()
    fun addToCart(id: Long, count: Int) {
        viewModelScope.launch {
            makeRequest(addToCartResp) {
                api.addToBasket(id, count)
            }
        }
    }

     val removeFromCartResp = MutableLiveData<RequestState<Any>>()
    fun removeFromCart(id: Long, count: Int) {
        viewModelScope.launch {
            makeRequest(removeFromCartResp) {
                api.removeItemFromBasket(id, count)
            }
        }
    }


}
