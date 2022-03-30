package uz.magnumactive.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class TypeName(
    @SerializedName("en")  val en: String?=null,
    @SerializedName("ru")  val ru: String?=null,
    @SerializedName("uz")  val uz: String?=null
)