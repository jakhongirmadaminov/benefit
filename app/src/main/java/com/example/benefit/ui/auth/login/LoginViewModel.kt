package com.example.benefit.ui.auth.login

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jahon on 03-Sep-20
 */
class LoginViewModel @ViewModelInject constructor(private val userRemote: UserRemote) :
    ViewModel() {

    val loginResp = SingleLiveEvent<String>()
    val errorMessage = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()

    fun login(phoneNumber: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.login(phoneNumber)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    InProgress -> {}
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        loginResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }



}