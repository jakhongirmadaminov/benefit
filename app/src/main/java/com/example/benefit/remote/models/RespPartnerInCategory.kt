package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespPartnerInCategory(
    @SerializedName("category") val category: Any? = null,
    @SerializedName("partners") val partners: List<Partner>? = null
)