package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespAcceptTerms(
    @SerializedName("order_card_id") val order_card_id: Int,
    @SerializedName("result") val result: String
)