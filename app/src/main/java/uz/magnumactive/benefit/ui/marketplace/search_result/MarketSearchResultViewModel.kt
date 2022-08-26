//package uz.magnumactive.benefit.ui.marketplace.search_result
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import uz.magnumactive.benefit.remote.AuthApiService
//import uz.magnumactive.benefit.remote.models.MarketAllSubCategoryDTO
//import uz.magnumactive.benefit.remote.models.MarketCategoryDTO
//import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
//import uz.magnumactive.benefit.remote.models.MarketProductDTO
//import uz.magnumactive.benefit.ui.marketplace.dialogs.MarketFilterBSD
//import uz.magnumactive.benefit.util.RequestState
//import uz.magnumactive.benefit.util.makeRequest
//import javax.inject.Inject
//
//@HiltViewModel
//class MarketSearchResultViewModel @Inject constructor(private val authClient: AuthApiService) :
//    ViewModel() {
//
//
//
//
//
//    val searchResult = MutableLiveData<RequestState<List<MarketProductDTO>>>()
//
//    fun getProductsForCategory() {
//        viewModelScope.launch {
//            makeRequest(searchResult) {
//                authClient.getMarketProductsByCategory(
//                    selectedCatg.id!!,
//                    selectedSubCatg?.id,
//                    filterSelection.type
//                )
//            }
//        }
//    }
//
//
//
//}