package com.example.benefit.remote.models
import com.google.gson.annotations.SerializedName

data class RespPid2Pid(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("currency")
    val currency: Int?,
    @SerializedName("date12")
    val date12: String?,
    @SerializedName("date7")
    val date7: String?,
    @SerializedName("expiry")
    val expiry: String?,
    @SerializedName("ext")
    val ext: String?,
    @SerializedName("field38")
    val field38: String?,
    @SerializedName("field48")
    val field48: Any?,
    @SerializedName("field91")
    val field91: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("merchantId")
    val merchantId: String?,
    @SerializedName("pan")
    val pan: String?,
    @SerializedName("pan2")
    val pan2: String?,
    @SerializedName("refNum")
    val refNum: String?,
    @SerializedName("resp")
    val resp: Int?,
    @SerializedName("respSV")
    val respSV: String?,
    @SerializedName("respText")
    val respText: String?,
    @SerializedName("stan")
    val stan: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("terminalId")
    val terminalId: String?,
    @SerializedName("tranType")
    val tranType: String?,
    @SerializedName("username")
    val username: String?
){
    val amountWithoutTiyin: Long?
        get() = amount?.toString()?.dropLast(2)?.toLong()

}