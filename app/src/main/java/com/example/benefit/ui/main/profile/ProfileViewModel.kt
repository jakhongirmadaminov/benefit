package com.example.benefit.ui.main.profile


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.CurrencyDTO
import com.example.benefit.remote.models.NewsDTO
import com.example.benefit.util.RequestState
import com.example.benefit.util.makeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val apiAuth: AuthApiService) : ViewModel() {

    init {
        getCurrencies()
        getNews()
    }

    var currencyResp = MutableLiveData<RequestState<List<CurrencyDTO>>>()
    var newsResp = MutableLiveData<RequestState<List<NewsDTO>>>()

    fun getCurrencies() {
        viewModelScope.launch(IO) {
            makeRequest(currencyResp) { apiAuth.getCurrencies() }
        }
    }

    fun getNews() {
        viewModelScope.launch(IO) {
            makeRequest(newsResp) { apiAuth.getNews(1, 10) }
        }
    }

}