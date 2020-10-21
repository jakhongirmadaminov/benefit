package com.example.benefit.ui.select_card_type

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.RespAcceptTerms
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.SingleLiveEvent
import com.example.benefit.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jahon on 06-Sep-20
 */
class SelectCardTypeViewModel @ViewModelInject constructor(private val userRemote: UserRemote) :
    ViewModel() {

    var passportBitmap: Bitmap? = null
    private val _currentStep = MutableLiveData(1)
    val currentStep: LiveData<Int> = _currentStep

    fun setCurrentStep(step: Int) {
        _currentStep.value = step
    }

    fun nextStep() {
        _currentStep.value = _currentStep.value!! + 1
    }

    fun previousStep() {
        _currentStep.value = _currentStep.value!! - 1
    }


    val acceptTermsResp = SingleLiveEvent<RespAcceptTerms>()
    val errorMessage = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()

    fun acceptTerms() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.termsAccept()
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        acceptTermsResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }

    fun addPassportPhoto() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                userRemote.addPassportPhoto(acceptTermsResp.value!!.order_card_id, passportBitmap!!)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        acceptTermsResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }

}