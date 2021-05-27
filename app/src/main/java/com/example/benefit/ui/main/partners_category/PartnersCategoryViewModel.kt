package com.example.benefit.ui.main.partners_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.PartnersRemoteImpl
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.SingleLiveEvent
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