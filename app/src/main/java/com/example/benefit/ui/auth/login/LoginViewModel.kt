package com.example.benefit.ui.auth.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.RespLogin
import com.example.benefit.remote.models.RespLoginCode
import com.example.benefit.remote.models.RespLoginSms
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.preferences.edit

/**
 * Created by jahon on 03-Sep-20
 */
class LoginViewModel @ViewModelInject constructor(private val userRemote: UserRemote) :
    ViewModel() {

    val loginResp = SingleLiveEvent<RespLogin>()
    val loginSmsResp = SingleLiveEvent<RespLoginSms>()
    val loginCodeResp = SingleLiveEvent<RespLoginCode>()
    val errorMessage = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()

    var phoneNum = ""
    var code = 0

    fun login(phoneNumber: String) {
        this.phoneNum = phoneNumber
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.login(phoneNumber)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        code = response.value.sms_live!!
                        loginResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }

    fun loginsms(code: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.loginSms(phoneNum, code)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        AppPrefs.edit {
                            userToken = response.value.user_token
                            avatar = response.value.avatar
                            firstName = response.value.first_name!!
                            lastName = response.value.last_name!!
                            userId = response.value.user_id!!
                            phoneNumber = phoneNum
                        }
                        loginSmsResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }


    fun loginCode(code: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.loginCode(
                AppPrefs.userId,
                AppPrefs.userToken!!,
                AppPrefs.phoneNumber!!,
                code
            )
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        loginCodeResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }


}