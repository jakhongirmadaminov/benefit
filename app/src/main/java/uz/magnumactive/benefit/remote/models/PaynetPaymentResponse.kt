package uz.magnumactive.benefit.remote.models

import com.google.gson.annotations.SerializedName


data class PaynetPaymentResponse(
    @SerializedName("response") val response: List<PaymentResponse>? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("statusText") val statusText: String? = null,
    @SerializedName("time") val time: Long? = null,
    @SerializedName("transactionId") val transactionId: String? = null
)

data class PaymentResponse(
    @SerializedName("key") val key: String? = null,
    @SerializedName("labelRu") val labelRu: String? = null,
    @SerializedName("labelUz") val labelUz: String? = null,
    @SerializedName("value") val value: String? = null
)
