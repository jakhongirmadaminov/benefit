package com.example.benefit.ui.main.partners_category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.PartnersRemoteImpl
import com.example.benefit.remote.models.PartnerDTO
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PartnersCategoryViewModel @ViewModelInject constructor(private val partnersRepository: PartnersRemoteImpl) :
    ViewModel() {

    val partnersResp = SingleLiveEvent<ResultWrapper<List<PartnerDTO>>>()

    fun getPartners() {
        partnersResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRepository.getPartners()
            withContext(Main) {
                partnersResp.value = response
            }
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}