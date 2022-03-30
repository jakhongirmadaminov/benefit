package uz.magnumactive.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class ReqLoginSms(@SerializedName("phone_number") val phone_number:String,
                       @SerializedName("sms_code") val sms_code:String)