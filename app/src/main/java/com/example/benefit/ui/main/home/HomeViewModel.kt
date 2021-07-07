package com.example.benefit.ui.main.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.CardBankDTO
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.NewsDTO
import com.example.benefit.remote.models.PaynetCategory
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRemote: UserRemote) : ViewModel() {

    val paynetCatgResp = MutableLiveData<List<PaynetCategory>>()
    val errorMessage = MutableLiveData<String>()
    val isLoadingPaynetCategories = MutableLiveData<Boolean>()

    fun getPaynetCategories() {
        isLoadingPaynetCategories.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.paymentCategories()
            withContext(Dispatchers.Main) {
                isLoadingPaynetCategories.value = false
                when (response) {
                    is ResultError -> errorMessage.value = response.message
                    is ResultSuccess -> paynetCatgResp.value = response.value
                }.exhaustive
            }
        }
    }

    val newsResp = MutableLiveData<List<NewsDTO>>()
    val isLoadingNews = MutableLiveData<Boolean>()
    fun getNews(page: Int) {
        isLoadingNews.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.getNews(page)
            withContext(Dispatchers.Main) {
                isLoadingNews.value = false
                when (response) {
                    is ResultError -> errorMessage.value = response.message
                    is ResultSuccess -> newsResp.value = response.value
                }.exhaustive
            }
        }
    }


    val cardsResp = MutableLiveData<List<CardDTO>>()
    val isLoadingCards = MutableLiveData<Boolean>()
    fun getMyCards() {
        isLoadingCards.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.getMyCards()
            withContext(Dispatchers.Main) {
                isLoadingCards.value = false
                when (response) {
                    is ResultError -> errorMessage.value = response.message
                    is ResultSuccess -> {
//                        val bankCards =
//                            response.value.bank.flatten().associateBy { it.idString }
//
//                        response.value.benefit.forEachIndexed { index, cardDTO ->
//                            cardDTO.balance = bankCards[cardDTO.own_id!!]!!.balance
//                        }

                        cardsResp.value = response.value.getProperly()
                    }
                }.exhaustive
            }
        }
    }


}