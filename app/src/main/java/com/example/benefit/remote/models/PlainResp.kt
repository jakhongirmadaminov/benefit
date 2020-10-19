package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class PlainResp(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("status") val status: Int? = null
)