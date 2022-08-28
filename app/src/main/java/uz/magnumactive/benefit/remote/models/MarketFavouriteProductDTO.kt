package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarketFavouriteProductDTO(
    @SerializedName("created_at")
    val createdAt: Int? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("item_info")
    val itemInfo: MarketProductDTO? = null,
    ) : Parcelable