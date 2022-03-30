package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class AutoPaymentDTO(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("card_info")
    val cardInfo: CardInfo?,
    @SerializedName("created")
    val created: String?,
    @SerializedName("fields")
    val fields: String?,
    @SerializedName("from_cashback")
    val fromCashback: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_notify")
    val isNotify: String?,
    @SerializedName("is_notify_bool")
    val isNotifyBool: Boolean?,
    @SerializedName("near_date")
    val nearDate: String?,
    @SerializedName("provider_info")
    val providerInfo: PaynetMerchant?,
    @SerializedName("service_info")
    val serviceInfo: ServiceInfo?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: Int?,
    @SerializedName("type_name")
    val typeName: String?,
    @SerializedName("type_name_info")
    val typeNameInfo: String?,
    @SerializedName("user_id")
    val userId: Int?
) : Parcelable

@Parcelize
data class CardInfo(
    @SerializedName("card_bank")
    val cardBank: @RawValue List<Any>?,
    @SerializedName("card_id")
    val cardId: @RawValue List<Long>?,
    @SerializedName("card_pan")
    val cardPan: @RawValue List<String>?
) : Parcelable


@Parcelize
data class ServiceInfo(
    @SerializedName("maxAmount")
    val maxAmount: Int?,
    @SerializedName("minAmount")
    val minAmount: Int?,
    @SerializedName("own_id")
    val ownId: Long?,
    @SerializedName("own_order")
    val ownOrder: Int?,
    @SerializedName("provider_name")
    val providerName: ProviderName?,
    @SerializedName("service_fields")
    val serviceFields: List<ServiceField>?,
    @SerializedName("titleRu")
    val titleRu: String?,
    @SerializedName("titleUz")
    val titleUz: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
) : Parcelable
