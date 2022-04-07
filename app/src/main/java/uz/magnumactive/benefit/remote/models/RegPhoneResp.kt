package uz.magnumactive.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RegPhoneResp(
    @SerializedName("phone_number") val phone_number: String? = null,
    @SerializedName("result") val result: Boolean? = null,
    @SerializedName("sms_code") val sms_code: Int? = null,
    @SerializedName("user_id") val user_id: Long? = null,
    @SerializedName("user_token") val user_token: String? = null,
    @SerializedName("msg") val msg: String? = null
)