package com.example.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BenefitContactDTO(
    @SerializedName("user_id") val uer_id: Long? = null,
    @SerializedName("phone_number") val phone_number: String? = null,
    @SerializedName("fullname") val fullname: String? = null,
    @SerializedName("avatar_link") val avatar_link: String? = null,
    var isChecked: Boolean = false,
) : Parcelable



@Parcelize
class BenefitFriends : ArrayList<BenefitContactDTO>(), Parcelable