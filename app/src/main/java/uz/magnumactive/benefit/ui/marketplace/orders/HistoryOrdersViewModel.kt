package uz.magnumactive.benefit.ui.marketplace.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.ActiveOrderDTO
import uz.magnumactive.benefit.remote.models.HistoryOrderDTO
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
    class HistoryOrdersViewModel @Inject constructor(val apiService: AuthApiService) : ViewModel() {


    val historyOrders = MutableLiveData<RequestState<List<HistoryOrderDTO>>>()


    fun getHistoryOrders() {
        viewModelScope.launch { makeRequest(historyOrders) { apiService.getHistoryOrders() } }
    }


}