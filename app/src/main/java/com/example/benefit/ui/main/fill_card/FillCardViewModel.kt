package com.example.benefit.ui.main.fill_card


import androidx.lifecycle.ViewModel
import com.example.benefit.remote.UserRemoteImpl

/**
 * Created by jahon on 03-Sep-20
 */import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class FillCardViewModel @Inject constructor(private val userRemoteImpl: UserRemoteImpl) :
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