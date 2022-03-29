package com.example.benefit.ui.partner_home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benefit.remote.AuthApiService
import com.example.benefit.remote.models.Partner
import com.example.benefit.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.joda.time.Period
import javax.inject.Inject

@HiltViewModel
class PartnerHomeViewModel @Inject constructor(val apiService: AuthApiService) : ViewModel() {

    val error = SingleLiveEvent<String>()
    val partner = MutableLiveData<Partner>()

    fun sendLikeOrDislike(like: Boolean, partnerId: Long) {
        flow {
            emit(
                apiService.likeOrDislikePartner(if (like) "like" else "dislike", partnerId)
            )
        }.onEach {
            getLastPartnerLikes(partnerId)
        }.catch {
            error.postValue(it.localizedMessage)
        }.launchIn(viewModelScope)
    }


    fun partnerStoriesFlow(partnerId: Long) =
        flow {
            val fromMillis = System.currentTimeMillis() - Period(0, 1, 0, 0).millis
            emit(
                apiService.getPartnerStories(partnerId, fromMillis)
            )
        }

    fun getLastPartnerLikes(partnerId: Long) =
        flow {
            emit(apiService.getPartnerInfo(partnerId))
        }.onEach {
            it.result?.data?.let { partnerInfo ->
                partner.postValue(partnerInfo)
            }
        }.catch {
        }.launchIn(viewModelScope)

}