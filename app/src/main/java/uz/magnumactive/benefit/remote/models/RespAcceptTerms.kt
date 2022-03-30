package uz.magnumactive.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespAcceptTerms(
    @SerializedName("order_card_id") val order_card_id: Long,
    @SerializedName("result") val result: String
)