package com.example.benefit.ui.main.profile.profile_bsd

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
class ProfileBSDViewModel @ViewModelInject constructor(private val userRemoteImpl: UserRemoteImpl) :
    ViewModel() {

    val loginResp = SingleLiveEvent<ResultWrapper<String>>()
    fun login(phoneNumber: String) {
        loginResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemoteImpl.login(phoneNumber)
            withContext(Dispatchers.Main) {
                loginResp.value = response
            }
        }
    }


}