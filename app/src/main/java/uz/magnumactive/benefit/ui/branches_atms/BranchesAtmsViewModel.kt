package uz.magnumactive.benefit.ui.branches_atms


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.models.BankBranchDTO
import uz.magnumactive.benefit.remote.repository.PartnersRemote
import uz.magnumactive.benefit.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BranchesAtmsViewModel @Inject constructor(private val partnersRemote: PartnersRemote) :
    ViewModel() {

    val branches = MutableLiveData<ResultWrapper<List<BankBranchDTO>>>()
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun getBranches() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            branches.postValue(partnersRemote.getAllBankBranches())
        }
    }


}