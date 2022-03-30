package uz.magnumactive.benefit.remote.models

import android.os.Build
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.util.loadImageUrl
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardDTO(
    @SerializedName("api_user_id") val api_user_id: Int? = null,
    @SerializedName("api_user_token") val api_user_token: String? = null,
    @SerializedName("background_id") val background_id: Int? = null,
    @SerializedName("background_link") val background_link: String? = null,
    @SerializedName("balance") var balance: String? = null,
    @SerializedName("card_status") val card_status: Int? = null,
    @SerializedName("card_title") val card_title: String? = null,
    @SerializedName("created") val created: Int? = null,
    @SerializedName("expiry") val expiry: Int? = null,
    @SerializedName("fullName") val fullName: String? = null,
    @SerializedName("id") val id: Long? = null,
    @SerializedName("own_id") val own_id: String? = null,
    @SerializedName("pan") val panHidden: String? = null,
    @SerializedName("card_pan") val panOpen: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("status") val status: Int? = null
) : Parcelable {
//
//    val balanceWithoutTiyin: String?
//        get() {
//            val balanceTemp = balance
//            return balanceTemp?.dropLast(2)
//        }

    fun isSupreme(): Boolean {
        return panOpen!!.contains("8600577")
    }

    fun setBackgroundInto(imageView: ImageView, tvLabel: TextView? = null) {
        if (background_link.isNullOrBlank() || background_link.contains("No background for this card")) {
            if (panOpen!!.contains("8600577")) {
                tvLabel?.text = "Supreme"
                imageView.setImageResource(R.drawable.card_bg_supreme)
            } else if (panOpen.contains("860057")) {
                tvLabel?.text = "Zoom"
                imageView.setImageResource(R.drawable.card_bg_zoom)
            } else {
                tvLabel?.text = ""
                imageView.setImageResource(R.drawable.card_bg_generic)
            }
        } else {
            imageView.loadImageUrl(background_link)
        }
    }

    fun setMiniBackgroundInto(imageView: ImageView, tvLabel: TextView? = null) {
        if (background_link.isNullOrBlank() || background_link.contains("No background for this card")) {
            if (panOpen!!.contains("8600577")) {
                tvLabel?.text = "Supreme"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageView.foreground =
                        ContextCompat.getDrawable(
                            imageView.context,
                            R.drawable.card_bg_mini_supreme
                        )
                } else {
                    imageView.setImageResource(R.drawable.card_bg_mini_supreme)
                }
            } else if (panOpen.contains("860057")) {
                tvLabel?.text = "Zoom"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageView.foreground =
                        ContextCompat.getDrawable(
                            imageView.context,
                            R.drawable.card_bg_mini_zoom
                        )
                } else {
                    imageView.setImageResource(R.drawable.card_bg_mini_zoom)
                }

            } else {
                tvLabel?.text = ""
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageView.foreground =
                        ContextCompat.getDrawable(
                            imageView.context,
                            R.drawable.card_bg_mini_generic
                        )
                } else {
                    imageView.setImageResource(R.drawable.card_bg_mini_generic)
                }
            }
        } else {
            imageView.loadImageUrl(background_link)
        }
    }
}

@Parcelize
class CardsDTO(val cards: List<CardDTO>? = null) : ArrayList<CardDTO>(cards ?: listOf()), Parcelable