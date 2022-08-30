package uz.magnumactive.benefit.remote.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderItem(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("created")
    val created: Int? = null,
    @SerializedName("created_text")
    val createdText: String? = null,
    @SerializedName("item_info")
    val itemInfo: ItemInfo? = null,
    @SerializedName("shop_item_id")
    val shopItemId: Int? = null,
    @SerializedName("summa")
    val summa: Int? = null,
    @SerializedName("total_summa")
    val totalSumma: Int? = null
):Parcelable