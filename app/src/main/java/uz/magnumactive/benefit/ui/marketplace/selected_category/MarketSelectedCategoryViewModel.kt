package uz.magnumactive.benefit.ui.marketplace.selected_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.*
import uz.magnumactive.benefit.ui.marketplace.cart.BaseBasketViewModel
import uz.magnumactive.benefit.ui.marketplace.dialogs.MarketFilterBSD
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
class MarketSelectedCategoryViewModel @Inject constructor(private val authClient: AuthApiService) :
    BaseBasketViewModel(authClient) {

    lateinit var selectedCatg: MarketPlaceCategoryObj
    val searchResult = MutableLiveData<RequestState<SearchResultDTO>>()

    var selectedSubCatg: MarketCategoryDTO? = null
        set(value) {
            field = value
            categoryProductsResult.value = null
            getProductsForCategory()
        }
    var filterSelection = MarketFilterBSD.FilterEnum.LATEST
        set(value) {
            field = value
            categoryProductsResult.value = null
            getProductsForCategory()
        }

    val subCategories = MutableLiveData<RequestState<MarketAllSubCategoryDTO>>()
    val categoryProductsResult = MutableLiveData<PagingData<MarketProductDTO>>()

    fun getProductsForCategory() {
        selectedSubCatg?.let{
            viewModelScope.launch {
                Pager(initialKey = 1,config = PagingConfig(pageSize = 30, prefetchDistance = 2),
                    pagingSourceFactory = {
                        SubCatgMarketProductDTOsPagingDataSource(
                            selectedCatg.id!!,
                            selectedSubCatg?.id,
                            filterSelection.type,
                            authClient
                        )
                    }
                ).flow.onEach {
                    categoryProductsResult.value = it
                }.cachedIn(viewModelScope).launchIn(viewModelScope)
            }
        } ?:run {
            viewModelScope.launch {
                Pager(config = PagingConfig(pageSize = 30, prefetchDistance = 2),
                    pagingSourceFactory = {
                        MarketProductDTOsPagingDataSource(
                            selectedCatg.id!!,
                            filterSelection.type,
                            authClient
                        )
                    }
                ).flow.onEach {
                    categoryProductsResult.value = it
                }.cachedIn(viewModelScope).launchIn(viewModelScope)
            }
        }
    }

    fun getSubcategoriesFor() {
        viewModelScope.launch {
            makeRequest(subCategories) { authClient.getSubCategory(selectedCatg.id!!) }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            makeRequest(searchResult) { authClient.searchProducts(query) }
        }
    }

}