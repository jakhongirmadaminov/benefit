package com.example.benefit.remote.models

import android.os.Parcelable
import com.example.benefit.ui.transactions_history.transaction_bsd.TransactionType
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionDTO(
    @SerializedName("title") val title: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("sum") val sum: Int? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("date") val date: Long? = null,
    var transactionType: TransactionType = TransactionType.NONE
) : Parcelable