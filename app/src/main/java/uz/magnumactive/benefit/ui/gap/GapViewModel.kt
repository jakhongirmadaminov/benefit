package uz.magnumactive.benefit.ui.gap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.GapGameDTO
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GapViewModel @Inject constructor(private val authApi: AuthApiService) : ViewModel() {

    val games = MutableLiveData<RequestState<List<GapGameDTO>>>()

    init {
        getGames()
    }

    fun getGames() {
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(games) {
                authApi.getGapGames()
            }
        }
    }

}