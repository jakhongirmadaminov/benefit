package com.example.benefit.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.RespLoginSms
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.preferences.edit
import javax.inject.Inject

@HiltViewModel
class SmsConfirmViewModel @Inject constructor(private val userRemote: UserRemote) : ViewModel() {

    val loginSmsResp = MutableLiveData<RespLoginSms>()
    val confirmNewCardResp = MutableLiveData<ResultWrapper<Any>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun loginsms(phoneNum: String, code: String) {
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

    fun confirmNewCard(cardId: Int, code: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.confirmNewCard(cardId, code)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        confirmNewCardResp.value = response
                    }
                }.exhaustive
            }
        }
    }


}