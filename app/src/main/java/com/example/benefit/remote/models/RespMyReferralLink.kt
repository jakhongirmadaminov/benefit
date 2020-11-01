package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespMyReferralLink(
    @SerializedName("referal_link_token") val referal_link_token: String,
    @SerializedName("result") val result: Boolean
)