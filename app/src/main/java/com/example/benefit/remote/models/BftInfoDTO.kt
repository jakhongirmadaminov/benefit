package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class BftInfoDTO(
    @SerializedName("balance_info")
    val balanceInfo: BalanceInfo?
)

data class BalanceInfo(
    @SerializedName("api_user_id")
    val apiUserId: Int?,
    @SerializedName("api_user_token")
    val apiUserToken: String?,
    @SerializedName("summa")
    val summa: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
