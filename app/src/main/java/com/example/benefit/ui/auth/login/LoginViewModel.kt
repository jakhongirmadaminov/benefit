package com.example.benefit.ui.auth.login

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Created by jahon on 03-Sep-20
 */
class LoginViewModel @ViewModelInject constructor(private val userRemote: UserRemote) :
    ViewModel() {

    val loginResp = SingleLiveEvent<ResultWrapper<String>>()
    fun login(phoneNumber: String) {
        loginResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.login(phoneNumber)
            withContext(Dispatchers.Main) {
                loginResp.value = response
            }
        }
    }

    fun uploadAvatar(bitmap: Bitmap) {


    }


}