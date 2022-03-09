package com.example.benefit.ui.partner_home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PartnerHomeViewModel @Inject constructor(val apiService: AuthApiService) : ViewModel() {

    val error = SingleLiveEvent<String>()

    fun sendLikeOrDislike(like: Boolean, partnerId: Long) {
        flow {
            emit(
                apiService.likeOrDislikePartner(if (like) "like" else "dislike", partnerId)
            )
        }.onEach {
            println()
        }.catch {
            error.postValue(it.localizedMessage)
        }.launchIn(viewModelScope)
    }

}