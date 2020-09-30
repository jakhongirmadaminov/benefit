package com.example.benefit.ui.expenses_by_categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.PartnersRemoteImpl
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpensesByCategoriesViewModel @ViewModelInject constructor(val partnersRemoteImpl: PartnersRemoteImpl): ViewModel() {


    val partnerCategoriesResp = SingleLiveEvent<ResultWrapper<List<PartnerCategoryDTO>>>()


    fun getPartnerCategories() {
        partnerCategoriesResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRemoteImpl.getPartnersCategory()
            withContext(Dispatchers.Main) {
                partnerCategoriesResp.value = response
            }
        }
    }
}