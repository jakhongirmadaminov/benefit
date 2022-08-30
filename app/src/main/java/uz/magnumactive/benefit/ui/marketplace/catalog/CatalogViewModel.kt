package uz.magnumactive.benefit.ui.marketplace.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.remote.models.SearchResultDTO
import uz.magnumactive.benefit.ui.marketplace.cart.BaseBasketViewModel
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(private val authClient: AuthApiService) : BaseBasketViewModel(authClient) {

    val searchResult = MutableLiveData<RequestState<SearchResultDTO>>()
    val catalogResult = MutableLiveData<RequestState<List<MarketPlaceCategoryObj>>>()
    val saleItemsResult = MutableLiveData<RequestState<List<MarketProductDTO>>>()

    fun getCatalog() {
        viewModelScope.launch {
            makeRequest(catalogResult) { authClient.getMarketplaceCategories() }
        }
    }

    fun getSaleItems() {
        viewModelScope.launch {
            makeRequest(saleItemsResult) { authClient.getMarketMainSales() }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            makeRequest(searchResult) { authClient.searchProducts(query) }
        }
    }


}