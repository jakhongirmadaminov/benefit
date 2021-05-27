package com.example.benefit.ui.main.profile.settings_bsd


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.UserRemoteImpl
import com.example.benefit.remote.models.RespLogin

import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jahon on 03-Sep-20
 */import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SettingsBSDViewModel @Inject constructor(private val userRemoteImpl: UserRemoteImpl) :
    ViewModel() {

    val loginResp = SingleLiveEvent<ResultWrapper<RespLogin>>()
    fun login(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemoteImpl.login(phoneNumber)
            withContext(Dispatchers.Main) {
                loginResp.value = response
            }
        }
    }


}