package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespLoanId(@SerializedName("responseBody") val responseBody: RespBody? = null)
data class RespBody(@SerializedName("loan_id") val loan_id: Long? = null)
