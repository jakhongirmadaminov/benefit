package uz.magnumactive.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class RespAddCard(
    @SerializedName("status") val status: Int,
    @SerializedName("card_id") val cardId: Long
)
