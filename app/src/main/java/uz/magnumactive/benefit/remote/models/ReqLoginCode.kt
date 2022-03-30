package uz.magnumactive.benefit.remote.models

import uz.magnumactive.benefit.util.AppPrefs
import com.google.gson.annotations.SerializedName

data class ReqLoginCode(
    @SerializedName("device_code") val device_code: String,
    @SerializedName("user_id") val user_id: Int = AppPrefs.userId,
    @SerializedName("user_token") val user_token: String = AppPrefs.userToken!!,
    @SerializedName("phone_number") val phone_number: String = AppPrefs.phoneNumber!!
)