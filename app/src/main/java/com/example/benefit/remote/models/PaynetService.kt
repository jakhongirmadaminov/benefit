package com.example.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


const val SERVICE_PAY_KEY = "оплата"

@Parcelize
data class PaynetService(
    @SerializedName("maxAmount") val maxAmount: Int? = null,
    @SerializedName("minAmount") val minAmount: Int? = null,
    @SerializedName("own_id") val own_id: Long? = null,
    @SerializedName("own_order") val own_order: Int? = null,
    @SerializedName("provider_name") val provider_name: ProviderName? = null,
    @SerializedName("service_fields") val service_fields: List<ServiceField>? = null,
    @SerializedName("titleRu") val titleRu: String? = null,
    @SerializedName("titleUz") val titleUz: String? = null,
    @SerializedName("updated_at") val updated_at: String? = null
) : Parcelable

fun List<PaynetService>.getPaymentService(): PaynetService? =
    firstOrNull { it.titleRu!!.lowercase().contains(SERVICE_PAY_KEY) }

@Parcelize
data class ProviderName(
    @SerializedName("image") val image: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("titleShort") val titleShort: String? = null
) : Parcelable

@Parcelize
data class ServiceField(
    @SerializedName("fieldControl") val fieldControl: String? = null,
    @SerializedName("fieldType") val fieldType: FieldType? = null,
    @SerializedName("fieldValues") val fieldValues: @RawValue Any? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("own_id") val own_id: Long? = null,
    @SerializedName("own_order") val own_order: Long? = null,
    @SerializedName("required") val required: Int? = null,
    @SerializedName("titleRu") val titleRu: String? = null,
    @SerializedName("titleUz") val titleUz: String? = null,
    var userSelection: String? = null
) : Parcelable

@Parcelize
data class ServiceFields(
    val data: List<ServiceField>
) : ArrayList<ServiceField>(data), Parcelable

//{"field_id":6789,"own_id":122,"titleRu":"АНДИЖОН ВИЛОЯТИ - Андижон шахри","titleUz":"АНДИЖОН ВИЛОЯТИ - Андижон шахри"}
@Parcelize
data class FieldValue(
    @SerializedName("field_id") val field_id: Double? = null,
    @SerializedName("own_id") val own_id: Double? = null,
    @SerializedName("titleRu") val titleRu: String? = null,
    @SerializedName("titleUz") val titleUz: String? = null
) : Parcelable

enum class FieldType {
    STRING,
    MONEY,
    DATEPOPUP,
    PHONE,
    REGEXBOX,
    NUMBER,
    LOTTOBOX,
    DATEBOX,
    PURCHASED,
    INFO,
    COMBO_INFO,
    SEARCH_BTN,
    COMBOMONTH,
    PREAMOUNT,
    DEBT_BTN,
    METHOD,
    KAFOLATDLG,
    SMARTCOMBO,
    COMBOBOX,
    LIST,
    WEBFRAME,
}