package com.example.benefit.ui.auth.registration

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.UserRemoteImpl
import com.example.benefit.remote.models.PlainResp
import com.example.benefit.remote.models.RegPhoneResp
import com.example.benefit.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jahon on 03-Sep-20
 */
class RegistrationViewModel @ViewModelInject constructor(private val userRemoteImpl: UserRemoteImpl) :
    ViewModel() {


    var phone = ""

    var signUpResp = MutableLiveData<RegPhoneResp>()
    val isLoading = SingleLiveEvent<Boolean>()
    val errorMessage = SingleLiveEvent<String>()
    val resendCodeResp = SingleLiveEvent<String>()
    val checkCodeResp = SingleLiveEvent<PlainResp>()


    fun signup(phoneNumber: String) {
        phone = phoneNumber
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemoteImpl.signup(phoneNumber)
            withContext(Dispatchers.Main) {
                when (response) {
                    InProgress -> {
                    }
                    is ResultError -> {
                        errorMessage.value = response.message
                        isLoading.value = false
                    }
                    is ResultSuccess -> {
                        signUpResp.value = response.value
                        isLoading.value = false
                    }
                }.exhaustive
            }
        }
    }

    fun resendCode() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemoteImpl.resendCode(phone)
            withContext(Dispatchers.Main) {
                when (response) {
                    InProgress -> {
                    }
                    is ResultError -> {
                        errorMessage.value = response.message
                        isLoading.value = false
                    }
                    is ResultSuccess -> {
                        resendCodeResp.value = response.value
                        isLoading.value = false
                    }
                }.exhaustive
            }
        }

    }

    fun checkCode(code: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemoteImpl.checkcode(
                signUpResp.value!!.user_token!!,
                signUpResp.value!!.user_id!!,
                signUpResp.value!!.phone_number!!,
                code
            )
            withContext(Dispatchers.Main) {
                when (response) {
                    InProgress -> {
                    }
                    is ResultError -> {
                        errorMessage.value = response.message
                        isLoading.value = false
                    }
                    is ResultSuccess -> {
                        checkCodeResp.value = response.value
                        isLoading.value = false
                    }
                }.exhaustive
            }
        }

    }

}