package uz.magnumactive.benefit.ui.payments


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.PaynetCategory
import uz.magnumactive.benefit.remote.repository.UserRemote
import uz.magnumactive.benefit.util.ResultError
import uz.magnumactive.benefit.util.ResultSuccess
import uz.magnumactive.benefit.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
        private val userRemote: UserRemote,
        private val apiAuth: AuthApiService
) : ViewModel() {

     var transferAmountKey: String? = null

    val paynetCatgResp = MutableLiveData<List<PaynetCategory>>()
    val errorMessage = MutableLiveData<String>()
    val isLoadingPaynetCategories = MutableLiveData<Boolean>()

    init {
        getPaynetCategories()
    }

    fun getPaynetCategories() {
        isLoadingPaynetCategories.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.paymentCategories()
            isLoadingPaynetCategories.postValue(false)
            when (response) {
                is ResultError -> errorMessage.postValue(response.message)
                is ResultSuccess -> paynetCatgResp.postValue(response.value)
            }.exhaustive
        }
    }

    fun setTransferringAmount(name: String) {
        transferAmountKey = name
    }


}