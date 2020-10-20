package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class ReqLoginCode(@SerializedName("user_id") val user_id:Int,
                        @SerializedName("user_token") val user_token:String,
                        @SerializedName("phone_number") val phone_number:String,
                        @SerializedName("device_code") val device_code:String)