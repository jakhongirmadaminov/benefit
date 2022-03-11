package com.example.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionInOutDTO(
    @SerializedName("income_total") val income_total: Long,
    @SerializedName("outcome_total") var outcome_total: Long
) : Parcelable

data class TransactionInOutDTOList(val list: ArrayList<TransactionInOutDTO>) :
    ArrayList<TransactionInOutDTO>(list)