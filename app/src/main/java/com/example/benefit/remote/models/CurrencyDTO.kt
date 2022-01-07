package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName


data class CurrencyDTO(
    @SerializedName("title") val title: String,
    @SerializedName("code") val code: String,
    @SerializedName("cb_price") val cb_price: String,
    @SerializedName("nbu_buy_price") val nbu_buy_price: String,
    @SerializedName("nbu_cell_price") val nbu_cell_price: String,
    @SerializedName("date") val date: String,
)
