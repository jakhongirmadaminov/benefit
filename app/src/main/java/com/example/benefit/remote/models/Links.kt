package com.example.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(
  @SerializedName("fb") val fb: String? = null,
  @SerializedName("inst") val inst: String? = null,
  @SerializedName("web") val web: String? = null
):Parcelable