package com.example.benefit.ui.main.profile.settings_bsd


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.ReqUserInfo
import com.example.benefit.remote.models.RespUserInfo
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.preferences.edit
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class SetingsMainViewModel @Inject constructor(
    private val userRemote: UserRemote,
    private val apiAuth: AuthApiService
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    var uploadUserInfoResp = MutableLiveData<RespUserInfo>()
    var userInfoResp = MutableLiveData<ResultWrapper<ReqUserInfo>>()

    init {
        viewModelScope.launch(IO) {
            userInfoResp.postValue(getFormattedResponse(isLoading) { apiAuth.getUserInfo(AppPrefs.userId) })
        }
    }

    fun updateUserInfo(
        name: String,
        lastName: String,
        gender: String = "male",
        dob: BigInteger = BigInteger.valueOf(AppPrefs.dobMillis)
    ) {
        isLoading.value = true
        viewModelScope.launch(IO) {
            val response = userRemote.updateUserInfo(name, lastName, gender, dob)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                        isLoading.value = false
                    }
                    is ResultSuccess -> {
                        AppPrefs.edit {
                            firstName = name
                            this.lastName = lastName
                            gender?.let {
                                this.gender = it
                            }
                            this.dobMillis = dobMillis
                        }
                        uploadUserInfoResp.value = response.value
                        isLoading.value = false
                    }
                }.exhaustive
            }
        }
    }


}