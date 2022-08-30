package uz.magnumactive.benefit.remote.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemInfo(
    @SerializedName("article_code")
    val articleCode: String? = null,
    @SerializedName("content")
    val content: TypeName? = null,
    @SerializedName("created_at")
    val createdAt: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("old_summa")
    val oldSumma: Int? = null,
//    @SerializedName("params")
//    val params: List<Any?>? = null,
    @SerializedName("photos")
    val photos: List<MarketProductPhotoDTO>? = null,
    @SerializedName("real_summa")
    val realSumma: Int? = null,
    @SerializedName("sale_percent")
    val salePercent: Int? = null,
    @SerializedName("sales_count")
    val salesCount: Int? = null,
    @SerializedName("seen_count")
    val seenCount: Int? = null,
    @SerializedName("shop_category_id")
    val shopCategoryId: Int? = null,
    @SerializedName("shop_category_name")
    val shopCategoryName: TypeName? = null,
    @SerializedName("shop_category_sub_id")
    val shopCategorySubId: Int? = null,
    @SerializedName("shop_category_sub_name")
    val shopCategorySubName: TypeName? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("title")
    val title: TypeName? = null,
    @SerializedName("updated_at")
    val updatedAt: Int? = null,
    @SerializedName("warehouse_count")
    val warehouseCount: Int? = null
):Parcelable