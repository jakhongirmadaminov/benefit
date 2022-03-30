package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.Constants.UZ
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaynetCategory(
    @SerializedName("image") val image: String? = null,
    @SerializedName("own_id") val own_id: Long? = null,
    @SerializedName("own_order") val own_order: Int? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("titleRu") val titleRu: String? = null,
    @SerializedName("titleUz") val titleUz: String? = null,
    @SerializedName("updated_at") val updated_at: String? = null,
    val imageResource: Int? = null,
    val paymentTypeEnum: EPaymentType? = null
) : Parcelable {
    fun getLocalizedTitle(): String {
        return if (AppPrefs.language == UZ) titleUz!! else titleRu!!
    }
}

@Parcelize
data class PaynetMerchant(
    @SerializedName("own_id") val own_id: Long? = null,
    @SerializedName("category_id") val category_id: Long? = null,
    @SerializedName("category_name") val category_name: LocalizedName? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("titleShort") val titleShort: String? = null,
    @SerializedName("updated_at") val updated_at: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("image2") val image2: String? = null,
) : Parcelable {
    fun getLocalizedCatgName(): String {
        return if (AppPrefs.language == UZ) category_name!!.uz!! else category_name!!.ru!!
    }
}

@Parcelize
data class LocalizedName(
    @SerializedName("ru") val ru: String? = null,
    @SerializedName("uz") val uz: String? = null,
) : Parcelable

enum class EPaymentType {
    CARD_TRANSFER, FRIEND_TRANSFER
}
