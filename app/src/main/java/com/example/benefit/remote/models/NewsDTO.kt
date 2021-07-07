package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class NewsDTO(
    @SerializedName("content") val content: String,
    @SerializedName("created") val created: String,
    @SerializedName("title") val title: String,
    @SerializedName("url_link") val url_link: String,
    @SerializedName("image") val image: String
)