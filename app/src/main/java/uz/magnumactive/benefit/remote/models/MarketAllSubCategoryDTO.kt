package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarketAllSubCategoryDTO(
    @SerializedName("category") val category: MarketCategoryDTO? = null,
    @SerializedName("sub_category") val subCategory: List<MarketCategoryDTO>? = null,
) : Parcelable

@Parcelize
data class MarketCategoryDTO(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("prior") val prior: Int? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("title") val title: TypeName? = null,
) : Parcelable
