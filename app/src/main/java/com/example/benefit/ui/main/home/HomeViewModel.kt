package com.example.benefit.ui.main.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.NewsDTO
import com.example.benefit.remote.models.PaynetCategory
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.SingleLiveEvent
import com.example.benefit.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(private val userRemote: UserRemote) : ViewModel() {

    val paynetCatgResp = SingleLiveEvent<List<PaynetCategory>>()
    val errorMessage = SingleLiveEvent<String>()
    val isLoadingPaynetCategories = SingleLiveEvent<Boolean>()

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

    val newsResp = SingleLiveEvent<List<NewsDTO>>()
    val isLoadingNews = SingleLiveEvent<Boolean>()
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


    val cardsResp = SingleLiveEvent<List<CardDTO>>()
    val isLoadingCards = SingleLiveEvent<Boolean>()
    fun getMyCards() {
        isLoadingCards.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRemote.getMyCards()
            withContext(Dispatchers.Main) {
                isLoadingCards.value = false
                when (response) {
                    is ResultError -> errorMessage.value = response.message
                    is ResultSuccess -> cardsResp.value = response.value.bank
                }.exhaustive
            }
        }
    }


}