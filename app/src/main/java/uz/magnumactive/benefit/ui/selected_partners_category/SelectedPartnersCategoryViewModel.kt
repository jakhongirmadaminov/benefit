package uz.magnumactive.benefit.ui.selected_partners_category


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.models.Partner
import uz.magnumactive.benefit.remote.repository.PartnersRemote
import uz.magnumactive.benefit.util.ResultWrapper
import uz.magnumactive.benefit.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

@ExperimentalSplittiesApi
@HiltViewModel
class SelectedPartnersCategoryViewModel @Inject constructor(private val partnersRepository: PartnersRemote) :
    ViewModel() {


    fun getPartnersForCategory(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRepository.getPartnersByCategoryId(id)
            withContext(Dispatchers.Main) {
                partnersResp.value = response
            }
        }
    }

    val partnersResp = SingleLiveEvent<ResultWrapper<List<Partner>>>()

}