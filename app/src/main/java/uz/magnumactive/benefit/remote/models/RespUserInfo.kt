package uz.magnumactive.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespUserInfo(
    @SerializedName("user_id") var user_id: Long,
    @SerializedName("access_token") var access_token: String,
    @SerializedName("user_token") var user_token: String,
    @SerializedName("phone_number") var phone_number: String? = null,
    @SerializedName("avatar") var avatar: String? = null
)
