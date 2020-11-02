package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

 data class RespFormatter<T>(@SerializedName("code") val code: Int? = null,
                           @SerializedName("message") val message: String? = null,
                           @SerializedName("error") val error: ErrorBody? = null,
                           @SerializedName("result") val result: T? = null)

 data class ErrorBody(@SerializedName("errorCode") val errorCode: Int? = null,
                           @SerializedName("message") val message: String? = null)