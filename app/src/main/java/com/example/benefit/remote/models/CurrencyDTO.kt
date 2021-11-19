package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName


data class CurrencyDTO(
    @SerializedName("title") val title: String,
    @SerializedName("code") val code: String,
    @SerializedName("cb_price") val cb_price: Double,
    @SerializedName("date") val date: String,
)
