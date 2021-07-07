package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespLogin(@SerializedName("sms_live") val sms_live: Int? = null)