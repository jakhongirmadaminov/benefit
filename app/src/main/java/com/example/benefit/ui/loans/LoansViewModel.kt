package com.example.benefit.ui.loans


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.LoanBody
import com.example.benefit.remote.models.RespLoanInfo
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.getFormattedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoansViewModel @Inject constructor(private val apiService: AuthApiService) : ViewModel() {


    val isLoading = MutableLiveData<Boolean>()
    var loanId: Long? = null

    val respLoanInfo = MutableLiveData<ResultWrapper<RespLoanInfo>>()

    fun getLoanIdByPan(pan: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val respLoanId = getFormattedResponse { apiService.getLoanIdByPan(pan) }

            when (respLoanId) {
                is ResultError -> {
                    isLoading.postValue(false)
                    respLoanInfo.postValue(ResultError())
                }
                is ResultSuccess -> {
                    loanId = respLoanId.value.responseBody?.loan_id
                    if (loanId != null) {
                        getLoanInfoById(loanId!!)
                    } else {
                        isLoading.postValue(false)
                        respLoanInfo.postValue(ResultError())
                    }
                }
            }
        }
    }

    fun getLoanInfoById(id: Long) {
        viewModelScope.launch {
            respLoanInfo.postValue(getFormattedResponse(isLoading) { apiService.getLoanInfo(id) })
        }
    }


}