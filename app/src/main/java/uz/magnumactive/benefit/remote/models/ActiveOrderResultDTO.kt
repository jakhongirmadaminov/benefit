package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActiveOrderResultDTO(
    @SerializedName("list") val list: ActiveOrderDTO
) : Parcelable
