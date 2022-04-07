package uz.magnumactive.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespLoginCode(
    @SerializedName("access_token") val access_token: String? = null,
    @SerializedName("result") val result: Boolean? = null,
    @SerializedName("user_id") val user_id: Long? = null,
    @SerializedName("user_token") val user_token: String? = null,
    @SerializedName("msg") val msg: String? = null
)