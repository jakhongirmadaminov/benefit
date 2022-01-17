package com.example.benefit.ui.gap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.GapGameDTO
import com.example.benefit.util.RequestState
import com.example.benefit.util.makeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GapViewModel @Inject constructor(private val authApi: AuthApiService) : ViewModel() {

    val games = MutableLiveData<RequestState<List<GapGameDTO>>>()

    init {
        getGames()
    }

    fun getGames() {
        viewModelScope.launch(Dispatchers.IO) {
            makeRequest(games) {
                authApi.getGapGames()
            }
        }
    }

}