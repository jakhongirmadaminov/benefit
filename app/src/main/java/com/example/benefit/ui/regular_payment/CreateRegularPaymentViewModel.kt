package com.example.benefit.ui.regular_payment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.benefit.remote.UserRemoteImpl

/**
 * Created by jahon on 03-Sep-20
 */
class CreateRegularPaymentViewModel @ViewModelInject constructor(private val userRemoteImpl: UserRemoteImpl) :
    ViewModel() {

//    val loginResp = SingleLiveEvent<ResultWrapper<String>>()
//    fun login(phoneNumber: String) {
//        loginResp.value = InProgress
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = userRemoteImpl.login(phoneNumber)
//            withContext(Dispatchers.Main) {
//                loginResp.value = response
//            }
//        }
//    }


}