package uz.magnumactive.benefit.remote.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActiveOrderDTO(
    @SerializedName("api_user_id")
    val apiUserId: Int? = null,
    @SerializedName("basket_summa")
    val basketSumma: Int? = null,
    @SerializedName("card_id")
    val cardId: Int? = null,
    @SerializedName("created")
    val created: Int? = null,
    @SerializedName("created_text")
    val createdText: String? = null,
    @SerializedName("delivery_summa")
    val deliverySumma: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("lan")
    val lan: String? = null,
    @SerializedName("lat")
    val lat: String? = null,
    @SerializedName("order_items")
    val orderItems: List<OrderItem>? = null,
    @SerializedName("payment_method")
    val paymentMethod: Int? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("status_text")
    val statusText: String? = null,
    @SerializedName("status_translate")
    val statusTranslate: TypeName? = null,
    @SerializedName("total_summa")
    val totalSumma: Int? = null,
    @SerializedName("user_address")
    val userAddress: String? = null
) : Parcelable