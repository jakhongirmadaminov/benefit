package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class BankBranchDTO(
    @SerializedName("end_time") val end_time: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("lan") val lan: String? = null,
    @SerializedName("lat") val lat: String? = null,
    @SerializedName("region_id") val region_id: Int? = null,
    @SerializedName("region_name_en") val region_name_en: String? = null,
    @SerializedName("region_name_ru") val region_name_ru: String? = null,
    @SerializedName("region_name_uz") val region_name_uz: String? = null,
    @SerializedName("short_en") val short_en: String? = null,
    @SerializedName("short_ru") val short_ru: String? = null,
    @SerializedName("short_uz") val short_uz: String? = null,
    @SerializedName("start_time") val start_time: String? = null,
    @SerializedName("title_en") val title_en: String? = null,
    @SerializedName("title_ru") val title_ru: String? = null,
    @SerializedName("title_uz") val title_uz: String? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("type_name") val type_name: TypeName? = null
)