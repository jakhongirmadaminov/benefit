package com.example.benefit.ui.regular_payment


/**
 * Created by jahon on 03-Sep-20
 */
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.*
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRegularPaymentViewModel @Inject constructor(
    private val userRemote: UserRemote,
    private val apiAuth: AuthApiService
) :
    ViewModel() {

    val deleteRegPaymentResp = MutableLiveData<RequestState<PlainResp>>()
    val paynetCatgResp = MutableLiveData<List<PaynetCategory>>()
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val isLoadingPaynetCategories = MutableLiveData<Boolean>()
    val paynetServices = MutableLiveData<RequestState<List<PaynetService>>>()

    init {
        getPaynetCategories()
        getMyCards()
    }


    val bftAndMyCardsPair = MutableLiveData<Pair<BalanceInfo, MyCardsResp>>()

    fun getMyCards() {
        viewModelScope.launch(IO) {
            val cardsResp = async { getFormattedResponse { apiAuth.getMyCards() } }
            val bftResp = async { getFormattedResponse { apiAuth.getBftInfo() } }

            processResponses(cardsResp.await(), bftResp.await())
        }
    }

    private fun processResponses(
        cardsResp: ResultWrapper<MyCardsResp>,
        bftResp: ResultWrapper<BftInfoDTO>
    ) {
        if (cardsResp is ResultSuccess && bftResp is ResultSuccess) {
            bftAndMyCardsPair.postValue(Pair(bftResp.value.balanceInfo!!, cardsResp.value))
        } else {
            errorMessage.postValue(
                (cardsResp as? ResultError)?.message ?: (bftResp as? ResultError)?.message
            )
        }
    }


    fun getPaynetServicesForProviderId(id: Long) {
        isLoading.value = true
        viewModelScope.launch(IO) {
            makeRequest(paynetServices) { apiAuth.getPaynetServices(id) }
        }
    }

    fun getPaynetCategories() {
        isLoadingPaynetCategories.value = true
        viewModelScope.launch(IO) {
            val response = userRemote.paymentCategories()
            isLoadingPaynetCategories.postValue(false)
            when (response) {
                is ResultError -> errorMessage.postValue(response.message)
                is ResultSuccess -> paynetCatgResp.postValue(response.value)
            }.exhaustive
        }
    }

    var transferAmountKey: String? = null

    val fields = arrayListOf<ServiceField>()
    fun areAllFieldsSelected(service: PaynetService): Boolean {
        fields.forEachIndexed { index, fieldValue ->
            if (service.service_fields!![index].required == 1 && fieldValue.userSelection.isNullOrBlank()) {
                return false
            }
        }
        return true
    }

    fun setTransferringAmount(name: String) {
        transferAmountKey = name
    }

    val savePaymentResp = MutableLiveData<RequestState<PlainResp>>()

    fun saveAutoPayment(
        type: Int,
        near_date: Long,
        title: String,
        card_id: Long?,
        provider_id: Long,
        service_id: Long,
        from_cashback: Boolean,
        is_notify: Boolean,
        amount: Int,
        fields: String
    ) {

        viewModelScope.launch {
            isLoading.value = true
            viewModelScope.launch(IO) {
                makeRequest(savePaymentResp) {
                    apiAuth.saveAutoPayment(
                        title = title,
                        card_id = card_id,
                        provider_id = provider_id,
                        service_id = service_id,
                        type = type,
                        from_cashback = from_cashback,
                        is_notify = is_notify,
                        amount = amount,
                        fields = fields,
                        near_date = near_date
                    )
                }
            }
        }

    }

    fun deleteRegularPayment(id: Int) {
        viewModelScope.launch(IO) {
            makeRequest(deleteRegPaymentResp) {
                apiAuth.deleteRegularPayment(id)
            }
        }
    }
}