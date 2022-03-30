package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class BenefitContactDTO(
    @SerializedName("user_id") val user_id: Long? = null,
    @SerializedName("phone_number") val phone_number: String? = null,
    @SerializedName("fullname") val fullname: String? = null,
    @SerializedName("avatar_link") val avatar_link: String? = null,
    var isChecked: Boolean = false,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is BenefitContactDTO -> {
                this.user_id == other.user_id &&
                        this.phone_number == other.phone_number &&
                        this.fullname == other.fullname &&
                        this.avatar_link == other.avatar_link
            }
            else -> false
        }
    }

}


@Parcelize
class BenefitFriends : ArrayList<BenefitContactDTO>(), Parcelable