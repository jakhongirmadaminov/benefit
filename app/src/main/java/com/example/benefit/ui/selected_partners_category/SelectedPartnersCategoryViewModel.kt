package com.example.benefit.ui.selected_partners_category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.PartnersRemoteImpl
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.util.InProgress
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectedPartnersCategoryViewModel @ViewModelInject constructor(private val partnersRepository: PartnersRemoteImpl) :
    ViewModel() {


    fun getPartnersForCategory(id: Int) {
        partnersResp.value = InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRepository.getPartnersForCategory(id)
            withContext(Dispatchers.Main) {
                partnersResp.value = response
            }
        }
    }

    val partnersResp = SingleLiveEvent<ResultWrapper<List<PartnerCategoryDTO>>>()

}