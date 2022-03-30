package uz.magnumactive.benefit.ui.main.partners_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.PartnersRemoteImpl
import uz.magnumactive.benefit.remote.models.PartnerCategoryDTO
import uz.magnumactive.benefit.util.ResultWrapper
import uz.magnumactive.benefit.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PartnersCategoryViewModel @Inject constructor(private val partnersRepository: PartnersRemoteImpl) :
    ViewModel() {

    val partnersResp = SingleLiveEvent<ResultWrapper<List<PartnerCategoryDTO>>>()

    fun getPartners() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRepository.getPartnersCategory()
            withContext(Main) {
                partnersResp.value = response
            }
        }
    }

}