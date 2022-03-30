package uz.magnumactive.benefit.remote.models

import uz.magnumactive.benefit.util.AppPrefs
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class ReqUserInfo(
    @SerializedName("first_name") var first_name: String,
    @SerializedName("last_name") var last_name: String,
    @SerializedName("gender") var gender: String?,
    @SerializedName("birth_day") var birth_day: BigInteger?,
    @SerializedName("user_id") var user_id: Int = AppPrefs.userId,
    @SerializedName("user_token") var user_token: String = AppPrefs.userToken!!
)
