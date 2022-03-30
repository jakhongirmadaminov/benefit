package uz.magnumactive.benefit.ui.main.profile.settings_bsd


/**
 * Created by jahon on 03-Sep-20
 */
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.PlainResp
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.ResultWrapper
import uz.magnumactive.benefit.util.getFormattedResponse
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