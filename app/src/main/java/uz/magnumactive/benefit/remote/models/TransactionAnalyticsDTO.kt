package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import uz.magnumactive.benefit.util.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.joda.time.format.DateTimeFormat

@Parcelize
data class TransactionAnalyticsContainerDTO(@SerializedName("content") val content: ArrayList<TransactionAnalyticsDTO>) :
    Parcelable

@Parcelize
data class TransactionAnalyticsDTO(
    @SerializedName("acctbal") val acctbal: Int?,
    @SerializedName("actamt") val actamt: Int?,
    @SerializedName("bankDate") val bankDate: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("conamt") val conamt: Int?,
    @SerializedName("credit") val credit: Boolean?,
    @SerializedName("currency") val currency: Int?,
    @SerializedName("hpan") val hpan: String?,
    @SerializedName("isCredit") val isCredit: Boolean?,
    @SerializedName("mcc") val mcc: Int?,
    @SerializedName("merchant") val merchant: String?,
    @SerializedName("merchantName") val merchantName: String?,
    @SerializedName("orgdev") val orgdev: Int?,
    @SerializedName("partner") val partner: Partner?,
    @SerializedName("reqamt") val reqamt: Int?,
    @SerializedName("resp") val resp: Int?,
    @SerializedName("reversal") val reversal: Boolean?,
    @SerializedName("street") val street: String?,
    @SerializedName("terminal") val terminal: String?,
    @SerializedName("transType") val transType: String?,
    @SerializedName("udate") val udate: Int?,
    @SerializedName("utime") val utime: Int?,
    @SerializedName("utrnno") val utrnno: Long?,
    @SerializedName("category_name") val categoryName: CategoryName?,
) : Parcelable {
//    "category_name":{"title_ru":"","title_en":"","title_uz":""}

    val amountWithoutTiyin: Long
        get() = if (reqamt != null && reqamt > 10) reqamt?.toString()?.dropLast(2)?.toLong() else 0


    val dateFormatted: String?
        get() = DateTimeFormat.forPattern("dd MMM yyyy")
            .print(DateTimeFormat.forPattern("yyyyMMdd").parseDateTime(udate.toString()))
    val timeFormatted: String?
        get() = DateTimeFormat.forPattern("HH:mm:ss")
            .print(
                DateTimeFormat.forPattern("HHmmss")
                    .parseDateTime(if (utime.toString().length == 5) "0" + utime.toString() else utime.toString())
            )
}

@Parcelize
data class CategoryName(
    @SerializedName("title_en") val titleEn: String? = null,
    @SerializedName("title_ru") val titleRu: String? = null,
    @SerializedName("title_uz") val titleUz: String? = null
) : Parcelable {

    fun getLocalized(locale: String): String? {
        return if (locale == Constants.EN) {
            titleEn
        } else if (locale == Constants.UZ) {
            titleUz
        } else {
            titleRu
        }
    }
}