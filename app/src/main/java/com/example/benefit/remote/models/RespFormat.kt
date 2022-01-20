package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespFormat<T>(
    @SerializedName("result") val result: ResultBody<T>? = null,
    @SerializedName("error") val error: ErrorBody? = null
)


data class ErrorBody(
    @SerializedName("errorCode") val errorCode: Int? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: ErrorBodyData? = null,
)
data class ErrorBodyData(
    @SerializedName("message") val message: String? = null,
)

data class ResultBody<T>(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("error") val error: ErrorBody? = null,
    @SerializedName("data") val data: T? = null
)