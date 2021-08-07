package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class TransactionInOutDTO(
    @SerializedName("income_total")   val income_total: Long,
    @SerializedName("outcome_total")  val outcome_total: Long
)
