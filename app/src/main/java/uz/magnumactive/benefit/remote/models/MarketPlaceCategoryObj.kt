package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarketPlaceCategoryObj(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("icon_image")
    val icon_image: String?,
    @SerializedName("prior")
    val prior: Int?,
    @SerializedName("is_sub")
    val is_sub: Int?,
    @SerializedName("subs")
    val subs: List<Sub>?,
    @SerializedName("title")
    val title: TypeName?,
) : Parcelable

@Parcelize
data class Sub(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("prior")
    val prior: Int?,
    @SerializedName("title")
    val title: TypeName?
) : Parcelable