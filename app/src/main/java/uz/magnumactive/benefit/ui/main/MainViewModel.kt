package uz.magnumactive.benefit.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiAuth: AuthApiService
) : ViewModel() {

    fun sendFCMToken(token: String) {
        viewModelScope.launch {
            apiAuth.sendFirebaseFCMToken(token)
        }
    }

}
