package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FriendDTO(
    @SerializedName("name") var name: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("photoUri") var photoUri: String? = null,
    var isChecked: Boolean = false
) : Parcelable


@Parcelize
class Friends : ArrayList<FriendDTO>(), Parcelable