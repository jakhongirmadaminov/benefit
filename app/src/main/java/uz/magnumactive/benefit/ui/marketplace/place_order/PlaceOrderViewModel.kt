package uz.magnumactive.benefit.ui.marketplace.place_order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.PreparedOrderDTO
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(val authApi: AuthApiService) : ViewModel() {


    val preparedOrderResp = MutableLiveData<RequestState<PreparedOrderDTO>>()

    fun getPreparedOrder() {
        viewModelScope.launch {
            makeRequest(preparedOrderResp) { authApi.prepareOrder() }
        }
    }


}
