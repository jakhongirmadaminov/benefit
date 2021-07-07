package com.example.benefit.ui.main.profile.settings_bsd


/**
 * Created by jahon on 03-Sep-20
 */
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.PlainResp
import com.example.benefit.util.AppPrefs
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.getFormattedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsBSDViewModel @Inject constructor(private val authApiService: AuthApiService) :
    ViewModel() {

    val newPasswordResp = MutableLiveData<ResultWrapper<PlainResp>>()
    val newPasswordLoading = MutableLiveData<Boolean>()
    fun setNewCode(newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            newPasswordResp.postValue(getFormattedResponse(newPasswordLoading) {
                authApiService.changePassword(
                    AppPrefs.phoneNumber!!,
                    newPassword,
                    AppPrefs.userId
                )
            })
        }
    }


}