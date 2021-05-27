package com.example.benefit.ui.order_card


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.BankBranchDTO
import com.example.benefit.remote.repository.PartnersRemote
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class BSDSelectBranchViewModel @Inject constructor(private val partnersRemote: PartnersRemote) :
    ViewModel() {

    val branches = MutableLiveData<List<BankBranchDTO>>()
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()


    fun getBranches() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = partnersRemote.getAllBankBranches()
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        branches.value = response.value
                    }
                }.exhaustive
            }
        }


    }

}
