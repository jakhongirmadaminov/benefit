package com.example.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Partner(
    @SerializedName("id") val id: Long,
    @SerializedName("category_id") val category_id: Int? = null,
    @SerializedName("coords") val coords: String? = null,
    @SerializedName("created_at") val created_at: Int? = null,
    @SerializedName("desc_en") val desc_en: String? = null,
    @SerializedName("desc_ru") val desc_ru: String? = null,
    @SerializedName("desc_uz") val desc_uz: String? = null,
    @SerializedName("icon_image") val icon_image: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("links") val links: Links? = null,
    @SerializedName("merchant_id") val merchant_id: Int? = null,
    @SerializedName("percent") val percent: Int? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("terminal_id") val terminal_id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("type_name") val type_name: String? = null,
    @SerializedName("updated_at") val updated_at: Int? = null,
    @SerializedName("user_id") val user_id: Int? = null,
    @SerializedName("likes_count") val likes_count: Int? = null,
    @SerializedName("photos_array") val photos_array: List<PartnerPhotoDTO>? = null,
    @SerializedName("last_likes") val last_likes: List<LikedUserDTO>? = null,
    @SerializedName("coords_array") val coords_array: LatLngDTO? = null,
) : Parcelable

@Parcelize
data class PartnerPhotoDTO(
    @SerializedName("image") val image: String? = null,
    @SerializedName("title_ru") val title_ru: String? = null,
    @SerializedName("title_en") val title_en: String? = null,
    @SerializedName("title_uz") val title_uz: String? = null,
) : Parcelable

@Parcelize
data class LatLngDTO(
    @SerializedName("lat") val lat: String? = null,
    @SerializedName("lan") val lan: String? = null,
) : Parcelable

@Parcelize

data class LikedUserDTO(
    @SerializedName("partner_id") val partner_id: Long? = null,
    @SerializedName("user_id") val user_id: Long? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("created") val created: String? = null,
    @SerializedName("user_name") val user_name: String? = null,
    @SerializedName("user_image") val user_image: String? = null,
    @SerializedName("created_text") val created_text: String? = null,
) : Parcelable