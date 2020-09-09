package com.example.benefit.ui.auth.registration

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.UserRemoteImpl
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jahon on 03-Sep-20
 */
class RegistrationViewModel @ViewModelInject constructor(private val userRemoteImpl: UserRemoteImpl) :
    ViewModel() {


    var phone = ""

    val signUpResp = SingleLiveEvent<ResultWrapper<String>>()
    val resendCodeResp = SingleLiveEvent<ResultWrapper<String>>()
    val loginCodeResp = SingleLiveEvent<ResultWrapper<String>>()


    fun signup(phoneNumber: String) {
        phone = phoneNumber
        signUpResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemoteImpl.signup(phoneNumber)
            withContext(Dispatchers.Main) {
                signUpResp.value = response
            }
        }
    }

    fun resendCode() {
        resendCodeResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemoteImpl.resendCode(phone)
            withContext(Dispatchers.Main) {
                resendCodeResp.value = response
            }
        }

    }

    fun loginCode(code: String) {
        loginCodeResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemoteImpl.loginCode(phone, code)
            withContext(Dispatchers.Main) {
                loginCodeResp.value = response
            }
        }

    }

}