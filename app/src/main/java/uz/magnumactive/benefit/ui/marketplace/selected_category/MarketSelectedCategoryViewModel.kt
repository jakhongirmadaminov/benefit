package uz.magnumactive.benefit.ui.marketplace.selected_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
class MarketSelectedCategoryViewModel @Inject constructor(private val authClient: AuthApiService) :
    ViewModel() {


    val subCategories = MutableLiveData<RequestState<List<MarketPlaceCategoryObj>>>()
    val categoryProductsResult = MutableLiveData<RequestState<List<MarketProductDTO>>>()

    fun getProductsForCategory(id: Long) {
        viewModelScope.launch {
            makeRequest(categoryProductsResult) { authClient.getMarketProductsByCategory(id) }
        }
    }
    fun getSubcategoriesFor(id: Long) {
        viewModelScope.launch {
            makeRequest(categoryProductsResult) { authClient.getMarketProductsByCategory(id) }
        }
    }

}