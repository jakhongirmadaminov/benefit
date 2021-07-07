package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class CardConfirmBody(
    @SerializedName("cardid") val cardId: Int,
    @SerializedName("sms_code") val smsCode: String
)
