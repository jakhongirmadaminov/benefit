package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarketBasketProductDTO(
    @SerializedName("created")
    val createdAt: Int? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("item_info")
    val itemInfo: MarketProductDTO? = null,
) : Parcelable