package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class CardDesignDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String
)