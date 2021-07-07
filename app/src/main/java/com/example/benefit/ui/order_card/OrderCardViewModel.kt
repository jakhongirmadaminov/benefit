package com.example.benefit.ui.order_card

/**
 * Created by jahon on 06-Sep-20
 */
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.RespAcceptTerms
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.SingleLiveEvent
import com.example.benefit.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderCardViewModel @Inject constructor(private val userRemote: UserRemote) :
    ViewModel() {

    var passportBitmap: Bitmap? = null
    var withPassportBitmap: Bitmap? = null
    var workProofBitmap: Bitmap? = null
    var innBitmap: Bitmap? = null
    private val _currentStep = MutableLiveData(1)
    val currentStep: LiveData<Int> = _currentStep

    fun setCurrentStep(step: Int) {
        _currentStep.value = step
    }

    fun nextStep() {
        _currentStep.value = _currentStep.value!! + 1
    }

    fun previousStep() {
        _currentStep.value = _currentStep.value!! - 1
    }


    val acceptTermsResp = SingleLiveEvent<RespAcceptTerms>()
    val currentSuccessPos = MutableLiveData<Int>()

    val errorMessage = SingleLiveEvent<String>()
    val isLoading = MutableLiveData<Boolean>()
    val isCompleted = SingleLiveEvent<Boolean>()

    fun acceptTerms(type_id: Int) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.termsAccept(type_id)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        acceptTermsResp.value = response.value
                        currentSuccessPos.value = currentStep.value!! - 1
                    }
                }.exhaustive
            }
        }
    }

    fun addPassportPhoto() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                userRemote.addPassportPhoto(acceptTermsResp.value!!.order_card_id, passportBitmap!!)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        acceptTermsResp.value = response.value
                        currentSuccessPos.value = currentStep.value!! - 1
                    }
                }.exhaustive
            }
        }
    }


    fun addWithPassportPhoto() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                userRemote.addPassportPhoto(acceptTermsResp.value!!.order_card_id, passportBitmap!!)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        acceptTermsResp.value = response.value
                        currentSuccessPos.value = currentStep.value!! - 1
                    }
                }.exhaustive
            }
        }
    }


    fun addWorkProof() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                userRemote.addWorkProof(acceptTermsResp.value!!.order_card_id, workProofBitmap!!)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        acceptTermsResp.value = response.value
                        currentSuccessPos.value = currentStep.value!! - 1
                    }
                }.exhaustive
            }
        }
    }

    fun addAddress(address: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                userRemote.addOrderCardAddress(acceptTermsResp.value!!.order_card_id, address)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        acceptTermsResp.value = response.value
                        currentSuccessPos.value = currentStep.value!! - 1
                    }
                }.exhaustive
            }
        }
    }


    fun addLimitSum(sum: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                userRemote.addLimitSum(acceptTermsResp.value!!.order_card_id, sum)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        acceptTermsResp.value = response.value
                        currentSuccessPos.value = currentStep.value!! - 1
                    }
                }.exhaustive
            }
        }
    }

    fun complete() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                userRemote.completeAddCard(acceptTermsResp.value!!.order_card_id)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResultError -> {
                        errorMessage.value = response.message
                    }
                    is ResultSuccess -> {
                        acceptTermsResp.value = response.value
                        isCompleted.value = true
                    }
                }.exhaustive
            }
        }
    }

}