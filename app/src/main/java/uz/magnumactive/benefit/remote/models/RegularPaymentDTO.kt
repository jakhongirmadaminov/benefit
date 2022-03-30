package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegularPaymentDTO(
    @SerializedName("name") val name: String? = null,
    @SerializedName("img") val img: String? = null,
) : Parcelable
