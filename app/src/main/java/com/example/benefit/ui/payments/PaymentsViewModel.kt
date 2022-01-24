package com.example.benefit.ui.payments


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.PaynetCategory
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
        private val userRemote: UserRemote,
        private val apiAuth: AuthApiService
) : ViewModel() {

     var transferAmountKey: String? = null

    val paynetCatgResp = MutableLiveData<List<PaynetCategory>>()
    val errorMessage = MutableLiveData<String>()
    val isLoadingPaynetCategories = MutableLiveData<Boolean>()

    init {
        getPaynetCategories()
    }

    fun getPaynetCategories() {
        isLoadingPaynetCategories.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.paymentCategories()
            isLoadingPaynetCategories.postValue(false)
            when (response) {
                is ResultError -> errorMessage.postValue(response.message)
                is ResultSuccess -> paynetCatgResp.postValue(response.value)
            }.exhaustive
        }
    }

    fun setTransferringAmount(name: String) {
        transferAmountKey = name
    }


}