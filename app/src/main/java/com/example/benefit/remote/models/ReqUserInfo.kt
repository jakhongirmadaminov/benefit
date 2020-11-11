package com.example.benefit.remote.models

import com.example.benefit.util.AppPrefs
import com.google.gson.annotations.SerializedName

data class ReqUserInfo(
    @SerializedName("first_name") var first_name: String,
    @SerializedName("last_name") var last_name: String,
    @SerializedName("gender") var gender: Int,
    @SerializedName("birth_day") var birth_day: Long,
    @SerializedName("user_id") var user_id: Int = AppPrefs.userId,
    @SerializedName("user_token") var user_token: String = AppPrefs.userToken!!
)
