package uz.magnumactive.benefit.ui.marketplace.selected_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.MarketAllSubCategoryDTO
import uz.magnumactive.benefit.remote.models.MarketCategoryDTO
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.ui.marketplace.BaseBasketViewModel
import uz.magnumactive.benefit.ui.marketplace.dialogs.MarketFilterBSD
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
class MarketSelectedCategoryViewModel @Inject constructor(private val authClient: AuthApiService) :
    BaseBasketViewModel(authClient) {

    lateinit var selectedCatg: MarketPlaceCategoryObj

    var selectedSubCatg: MarketCategoryDTO? = null
        set(value) {
            field = value
            getProductsForCategory()
        }
    var filterSelection = MarketFilterBSD.FilterEnum.LATEST
        set(value) {
            field = value
            getProductsForCategory()
        }


    val subCategories = MutableLiveData<RequestState<MarketAllSubCategoryDTO>>()
    val categoryProductsResult = MutableLiveData<RequestState<List<MarketProductDTO>>>()

    fun getProductsForCategory() {
        viewModelScope.launch {
            makeRequest(categoryProductsResult) {
                authClient.getMarketProductsByCategory(
                    selectedCatg.id!!,
                    selectedSubCatg?.id,
                    filterSelection.type
                )
            }
        }
    }


    fun getSubcategoriesFor() {
        viewModelScope.launch {
            makeRequest(subCategories) { authClient.getSubCategory(selectedCatg.id!!) }
        }
    }

}