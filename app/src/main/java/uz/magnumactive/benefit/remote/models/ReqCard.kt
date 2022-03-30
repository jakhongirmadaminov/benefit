package uz.magnumactive.benefit.remote.models

import uz.magnumactive.benefit.util.AppPrefs
import com.google.gson.annotations.SerializedName


data class ReqCard(
    @SerializedName("pan") val pan: String,
    @SerializedName("title") val title: String,
    @SerializedName("expiry") val expiry: String,
    @SerializedName("user_token") val user_token: String = AppPrefs.userToken!!,
    @SerializedName("user_id") val user_id: Int = AppPrefs.userId
)
