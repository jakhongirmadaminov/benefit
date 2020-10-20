package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class PaynetCategory(
    @SerializedName("image") val image: String?=null,
    @SerializedName("own_id") val own_id: Int?=null,
    @SerializedName("own_order") val own_order: Int?=null,
    @SerializedName("status") val status: Int?=null,
    @SerializedName("titleRu") val titleRu: String?=null,
    @SerializedName("titleUz") val titleUz: String?=null,
    @SerializedName("updated_at") val updated_at: String?=null,
    val imageResource: Int? = null,
    val paymentTypeEnum: EPaymentType? = null
)

enum class EPaymentType {
    CARD_TRANSFER, FRIEND_TRANSFER
}
