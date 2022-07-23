package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import uz.magnumactive.benefit.util.AppPrefs

@Parcelize
data class TypeName(
    @SerializedName("en") val en: String? = null,
    @SerializedName("ru") val ru: String? = null,
    @SerializedName("uz") val uz: String? = null
) : Parcelable {

    fun getLocalized(): String {
        return when (AppPrefs.language) {
            "en" -> en ?: ""
            "uz" -> uz ?: ""
            else -> ru ?: ""
        }
    }

}