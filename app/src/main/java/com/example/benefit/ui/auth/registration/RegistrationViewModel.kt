package com.example.benefit.ui.auth.registration

import android.graphics.Bitmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.RegPhoneResp
import com.example.benefit.remote.models.RespUserInfo
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.preferences.edit
import java.math.BigInteger

/**
 * Created by jahon on 03-Sep-20
 */import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userRemote: UserRemote) :
    ViewModel() {


    var phone = ""

    var signUpResp = SingleLiveEvent<RegPhoneResp>()
    var setPasswordResp = SingleLiveEvent<RespUserInfo>()
    var uploadAvatarResp = SingleLiveEvent<RespUserInfo>()
    var uploadUserInfoResp = SingleLiveEvent<RespUserInfo>()
    var addNewCardResp = SingleLiveEvent<Any>()
    val isLoading = SingleLiveEvent<Boolean>()
    val errorMessage = SingleLiveEvent<String>()
    val resendCodeResp = SingleLiveEvent<String>()
    val checkCodeResp = SingleLiveEvent<RespUserInfo>()


    fun signup(phoneNumber: String, referal: String? = null) {
        phone = phoneNumber
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.signup(phoneNumber, referal)
            withContext(Dispatchers.Main) {
                when (response) {
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
            val response = userRemote.resendCode(phone)
            withContext(Dispatchers.Main) {
                when (response) {
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
            val response = userRemote.checkcode(
                signUpResp.value!!.user_token!!,
                signUpResp.value!!.user_id!!,
                signUpResp.value!!.phone_number!!,
                code
            )
            withContext(Dispatchers.Main) {
                when (response) {
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


    fun setPassword(password: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.setPassword(
                password,
                checkCodeResp.value!!.phone_number!!,
                checkCodeResp.value!!.user_token,
                checkCodeResp.value!!.user_id
            )
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                        isLoading.value = false
                    }
                    is ResultSuccess -> {
                        AppPrefs.edit {
                            userId = response.value.user_id
                            userToken = response.value.user_token
                            phoneNumber = response.value.phone_number
                            token = response.value.access_token
                        }
                        setPasswordResp.value = response.value
                        isLoading.value = false
                    }
                }.exhaustive
            }
        }

    }

    fun uploadAvatar(bitmap: Bitmap) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.uploadAvatar(bitmap)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                        isLoading.value = false
                    }
                    is ResultSuccess -> {
                        AppPrefs.edit {
                            avatar = Constants.BASE_URL + "upload/" + response.value.avatar
                        }
                        uploadAvatarResp.value = response.value
                        isLoading.value = false
                    }
                }.exhaustive
            }
        }
    }

    fun uploadProfileInfo(name: String, lastName: String, gender: String, dobMillis: BigInteger) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.updateUserInfo(name, lastName, gender, dobMillis)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                        isLoading.value = false
                    }
                    is ResultSuccess -> {
                        uploadUserInfoResp.value = response.value
                        isLoading.value = false
                    }
                }.exhaustive
            }
        }
    }

    fun addNewCard( title: String,cardNumber: String, expiry: Int) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.addNewCard(title, cardNumber, expiry)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                        isLoading.value = false
                    }
                    is ResultSuccess -> {
                        addNewCardResp.value = response.value
                        isLoading.value = false
                    }
                }.exhaustive
            }
        }
    }

}