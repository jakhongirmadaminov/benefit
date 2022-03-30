package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardP2PDTO(
    @SerializedName("EMBOS_NAME") val fullName: String?,
    @SerializedName("CARDTYPE") var type: String? = null,
    @SerializedName("CARDSTATUS") val card_status: Int? = null,
    @SerializedName("EXP_DT") val expiry: String? = null,
    @SerializedName("CREF_NO") val pan: String? = null,
) : Parcelable
