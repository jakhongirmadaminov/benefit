package com.example.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PartnerCategoryDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("created_at") val created_at: Int,
    @SerializedName("updated_at") val updated_at: Int,
    @SerializedName("title_ru") val title_ru: String,
    @SerializedName("title_en") val title_en: String,
    @SerializedName("title_uz") val title_uz: String,
    @SerializedName("color") val color: String,
    @SerializedName("image") val image: String,
    @SerializedName("icon_image") val icon_image: String,
    @SerializedName("prior") val prior: Int,
    @SerializedName("is_sub") val is_sub: Int,
    @SerializedName("main_id") val main_id: Int? = null
) : Parcelable
