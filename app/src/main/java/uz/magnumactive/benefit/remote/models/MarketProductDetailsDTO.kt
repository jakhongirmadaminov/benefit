package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarketProductDetailsDTO(
    @SerializedName("in_elected")
    val isInFavourites: Boolean? = null,
    @SerializedName("product")
    val product: MarketProductDTO? = null,

    ) : Parcelable