package com.example.benefit.ui.gap.create_game


/**
 * Created by jahon on 03-Sep-20
 */
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.BenefitContactDTO
import com.example.benefit.remote.models.GapGameDTO
import com.example.benefit.util.RequestState
import com.example.benefit.util.makeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGameViewModel @Inject constructor(private val authApi: AuthApiService) : ViewModel() {

    var selectedFriends: ArrayList<BenefitContactDTO> = arrayListOf()
    val availableContacts = MutableLiveData<RequestState<List<BenefitContactDTO>>>()

    fun checkMyContacts(contactsList: String) {
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(availableContacts) {
                authApi.getBenefitFriends(contactsList)
            }
        }
    }

    val createGameResp = MutableLiveData<RequestState<GapGameDTO>>()

    fun createGame(
        members: String,
        title: String,
        summ: String,
        isRandom: String,
        isNotif: String,
        period: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(createGameResp) {
                authApi.createGapGame(title, summ, isRandom, isNotif, period, members)
            }
        }
    }

}