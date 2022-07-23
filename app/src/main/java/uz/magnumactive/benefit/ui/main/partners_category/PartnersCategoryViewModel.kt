package uz.magnumactive.benefit.ui.main.partners_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.PartnersRemoteImpl
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.remote.models.PartnerCategoryDTO
import uz.magnumactive.benefit.util.ResultWrapper
import uz.magnumactive.benefit.util.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class PartnersCategoryViewModel @Inject constructor(
    private val partnersRepository: PartnersRemoteImpl,
    private val api: AuthApiService
) :
    ViewModel() {

    val partnersResp = SingleLiveEvent<ResultWrapper<List<PartnerCategoryDTO>>>()
    val marketplaceCategories = MutableLiveData<List<MarketPlaceCategoryObj>?>()
    val isMarketplaceLoading = MutableLiveData<Boolean>()

    fun getPartners() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRepository.getPartnersCategory()
            withContext(Main) {
                partnersResp.value = response
            }
        }
    }

    fun getMarketCategories() {
        flow {
            emit(api.getMarketplaceCategories())
        }.onEach { response ->
            marketplaceCategories.postValue(response.result?.data)
            isMarketplaceLoading.postValue(false)
        }.catch {
            print(it.localizedMessage)
            isMarketplaceLoading.postValue(false)
        }.onStart {
            isMarketplaceLoading.postValue(true)
        }.launchIn(viewModelScope)
    }

}