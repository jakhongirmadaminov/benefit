package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class GapGameDTO(
    @SerializedName("created")
    val created: Int?,
    @SerializedName("created_text")
    val createdText: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_notif")
    val isNotif: Int?,
    @SerializedName("is_notif_text")
    val isNotifText: String?,
    @SerializedName("is_period")
    val isPeriod: Int?,
    @SerializedName("is_period_text")
    val isPeriodText: String?,
    @SerializedName("is_period_text_")
    val isPeriodText_: String?,
    @SerializedName("is_random")
    val isRandom: Int?,
    @SerializedName("is_random_text")
    val isRandomText: String?,
    @SerializedName("members")
    val members: List<Member>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("status_text")
    val statusText: String?,
    @SerializedName("summa")
    val summa: String?
) : Parcelable

@Parcelize
data class Member(
    @SerializedName("card_id")
    val cardId: Long?,
    @SerializedName("card_info")
    val cardInfo: @RawValue Any?,
    @SerializedName("created")
    val created: Int?,
    @SerializedName("created_text")
    val createdText: String?,
    @SerializedName("game_gap_id")
    val gameGapId: Int?,
    @SerializedName("prior_in_game")
    val priorInGame: Int?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("status_text")
    val statusText: String?,
    @SerializedName("type")
    val type: Int?,
    @SerializedName("type_text")
    val typeText: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("user_info")
    val userInfo: UserInfo?
) : Parcelable

@Parcelize
data class UserInfo(
    @SerializedName("avatar_link")
    val avatarLink: String?,
    @SerializedName("fullname")
    val fullname: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?
) : Parcelable


data class CardInfoDTO(
    @SerializedName("api_user_id")
    val apiUserId: Int?,
    @SerializedName("api_user_token")
    val apiUserToken: String?,
    @SerializedName("background_id")
    val backgroundId: Int?,
    @SerializedName("background_link")
    val backgroundLink: String?,
    @SerializedName("background_link_small")
    val backgroundLinkSmall: String?,
    @SerializedName("balance")
    val balance: String?,
    @SerializedName("card_bank")
    val cardBank: CardBank?,
    @SerializedName("card_pan")
    val cardPan: String?,
    @SerializedName("card_status")
    val cardStatus: Int?,
    @SerializedName("card_title")
    val cardTitle: String?,
    @SerializedName("card_type")
    val cardType: Int?,
    @SerializedName("card_type_name")
    val cardTypeName: String?,
    @SerializedName("created")
    val created: Int?,
    @SerializedName("expiry")
    val expiry: Int?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_cashback")
    val isCashback: Int?,
    @SerializedName("own_id")
    val ownId: String?,
    @SerializedName("pan")
    val pan: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("status")
    val status: Int?
)

data class CardBank(
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("mfo")
    val mfo: String?,
    @SerializedName("title")
    val title: String?
)