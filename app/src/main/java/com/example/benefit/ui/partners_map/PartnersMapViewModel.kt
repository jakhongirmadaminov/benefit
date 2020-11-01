package com.example.benefit.ui.partners_map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.Partner
import com.example.benefit.remote.repository.PartnersRemote
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.SingleLiveEvent
import com.example.benefit.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PartnersMapViewModel @ViewModelInject constructor(private val partnersRepository: PartnersRemote) :
    ViewModel() {

    var errorMessage = SingleLiveEvent<String?>()
    var isLoading = SingleLiveEvent<Boolean>()
    val partnersResp = SingleLiveEvent<List<Partner>>()

    fun getPartnersForCategory(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRepository.getPartnersByCategoryId(id)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        partnersResp.value = response.value
                    }
                }
            }.exhaustive
        }
    }
}


