package uz.magnumactive.benefit.remote.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PreparedOrderDTO(
    @SerializedName("basket_total_summa")
    val basketTotalSumma: Int? = null,
    @SerializedName("delivery_days")
    val deliveryDays: String? = null,
    @SerializedName("delivery_summa")
    val deliverySumma: Int? = null,
//    @SerializedName("payment_method")
//    val paymentMethod: PaymentMethod? = null,
    @SerializedName("total_summa")
    val totalSumma: Int? = null,
    @SerializedName("user_card")
    val userCard: List<CardDTO>? = null
) : Parcelable