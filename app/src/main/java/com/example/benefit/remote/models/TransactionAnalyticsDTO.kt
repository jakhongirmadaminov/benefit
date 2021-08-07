package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName


data class TransactionAnalyticsContainerDTO(@SerializedName("content") val content: List<TransactionAnalyticsDTO>)

data class TransactionAnalyticsDTO(
    @SerializedName("acctbal") val acctbal: Int?,
    @SerializedName("actamt") val actamt: Int?,
    @SerializedName("bankDate") val bankDate: Any?,
    @SerializedName("city") val city: String?,
    @SerializedName("conamt") val conamt: Int?,
    @SerializedName("credit") val credit: Boolean?,
    @SerializedName("currency") val currency: Int?,
    @SerializedName("hpan") val hpan: String?,
    @SerializedName("isCredit") val isCredit: Boolean?,
    @SerializedName("mcc") val mcc: Int?,
    @SerializedName("merchant") val merchant: String?,
    @SerializedName("merchantName") val merchantName: String?,
    @SerializedName("orgdev") val orgdev: Int?,
    @SerializedName("partner") val partner: Partner?,
    @SerializedName("reqamt") val reqamt: Int?,
    @SerializedName("resp") val resp: Int?,
    @SerializedName("reversal") val reversal: Boolean?,
    @SerializedName("street") val street: String?,
    @SerializedName("terminal") val terminal: String?,
    @SerializedName("transType") val transType: String?,
    @SerializedName("udate") val udate: Int?,
    @SerializedName("utime") val utime: Int?,
    @SerializedName("utrnno") val utrnno: Long?
)

data class CategoryName(
    @SerializedName("title_en") val titleEn: String?,
    @SerializedName("title_ru") val titleRu: String?,
    @SerializedName("title_uz") val titleUz: String?
)