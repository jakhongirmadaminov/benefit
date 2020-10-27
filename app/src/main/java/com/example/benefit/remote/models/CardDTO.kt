package com.example.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardDTO(
    @SerializedName("api_user_id") val api_user_id: Int? = null,
    @SerializedName("api_user_token") val api_user_token: String? = null,
    @SerializedName("background_id") val background_id: Int? = null,
    @SerializedName("background_link") val background_link: String? = null,
    @SerializedName("balance") val balance: String? = null,
    @SerializedName("card_status") val card_status: Int? = null,
    @SerializedName("card_title") val card_title: String? = null,
    @SerializedName("created") val created: Int? = null,
    @SerializedName("expiry") val expiry: Int? = null,
    @SerializedName("fullName") val fullName: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("own_id") val own_id: String? = null,
    @SerializedName("pan") val pan: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("status") val status: Int? = null
) : Parcelable