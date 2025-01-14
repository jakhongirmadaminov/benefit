package uz.magnumactive.benefit.ui.main.profile


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.CurrencyDTO
import uz.magnumactive.benefit.remote.models.NewsDTO
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.makeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val apiAuth: AuthApiService) : ViewModel() {

    var currencyResp = MutableLiveData<RequestState<List<CurrencyDTO>>>()
    var newsResp = MutableLiveData<RequestState<List<NewsDTO>>>()
    init {
        getCurrencies()
        getNews()
    }


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