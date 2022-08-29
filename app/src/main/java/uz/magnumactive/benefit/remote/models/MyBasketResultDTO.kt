package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyBasketResultDTO(
    @SerializedName("total_summa") val totalSum: Int? = null,
    @SerializedName("list") val products: List<MarketBasketProductDTO>? = null
) : Parcelable