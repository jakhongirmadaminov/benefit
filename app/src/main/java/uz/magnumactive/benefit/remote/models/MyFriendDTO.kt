package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyFriendDTO(
    @SerializedName("my_friend_user_id") var my_friend_user_id: Long? = null,
    @SerializedName("phone_number") var phone_number: String? = null,
    @SerializedName("fullname") var fullname: String? = null,
    @SerializedName("avatar_link") var avatar_link: String? = null,
    var isChecked: Boolean = false
) : Parcelable


@Parcelize
class MyFriends : ArrayList<MyFriendDTO>(), Parcelable