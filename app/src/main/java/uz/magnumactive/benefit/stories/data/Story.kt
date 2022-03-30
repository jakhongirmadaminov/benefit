package uz.magnumactive.benefit.stories.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story(
    @SerializedName("created")
    val created: String?,
    @SerializedName("end_time")
    val endTime: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("partner_icon_image")
    val partnerIconImage: String?,
    @SerializedName("partner_id")
    val partnerId: Int?,
    @SerializedName("partner_image")
    val partnerImage: String?,
    @SerializedName("partner_title")
    val partnerTitle: String?,
    @SerializedName("start_time")
    val startTime: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: Int?,
    @SerializedName("video_link")
    val videoLink: String?
) : Parcelable {

    fun isVideo() = videoLink?.isNotBlank() ?: false
}