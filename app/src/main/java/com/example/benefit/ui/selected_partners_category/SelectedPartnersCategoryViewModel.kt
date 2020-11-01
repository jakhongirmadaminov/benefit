package com.example.benefit.ui.selected_partners_category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.PartnersRemoteImpl
import com.example.benefit.remote.models.Partner
import com.example.benefit.remote.repository.PartnersRemote
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
class SelectedPartnersCategoryViewModel @ViewModelInject constructor(private val partnersRepository: PartnersRemote) :
    ViewModel() {


    fun getPartnersForCategory(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRepository.getPartnersByCategoryId(id)
            withContext(Dispatchers.Main) {
                partnersResp.value = response
            }
        }
    }

    val partnersResp = SingleLiveEvent<ResultWrapper<List<Partner>>>()

}