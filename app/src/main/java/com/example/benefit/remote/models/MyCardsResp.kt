package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class MyCardsResp(
    @SerializedName("bank") val bank: List<CardDTO>,
    @SerializedName("benefit") val benefit: List<CardDTO>,
)
