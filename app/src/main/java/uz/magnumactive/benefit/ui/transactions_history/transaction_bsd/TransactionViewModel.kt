package uz.magnumactive.benefit.ui.transactions_history.transaction_bsd


/**
 * Created by jahon on 03-Sep-20
 */
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.BenefitContactDTO
import uz.magnumactive.benefit.remote.models.BenefitFriends
import uz.magnumactive.benefit.remote.models.MyFriendDTO
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val authApi: AuthApiService) : ViewModel() {


    val availableContacts = MutableLiveData<RequestState<List<BenefitContactDTO>>>()
    var selectedContacts = BenefitFriends()

    fun checkMyContacts(contactsList: String) {
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(availableContacts) {
                authApi.getBenefitFriends(contactsList)
            }
        }
    }



//    val friendsResp = MutableLiveData<RequestState<List<MyFriendDTO>>>()
//
//    fun getFriends() {
//        viewModelScope.launch {
//            makeRequest(friendsResp) { authApi.myFriends() }
//        }
//    }

}