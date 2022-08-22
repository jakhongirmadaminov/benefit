package uz.magnumactive.benefit.remote.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarketProductParamDTO(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("shop_item_id")
    val shop_item_id: Long? = null,
    @SerializedName("shop_category_id")
    val shop_category_id: Long? = null,
    @SerializedName("shop_category_params_id")
    val shop_category_params_id: Long? = null,
    @SerializedName("name")
    val name: TypeName? = null,
    @SerializedName("value")
    val value: TypeName? = null
) : Parcelable