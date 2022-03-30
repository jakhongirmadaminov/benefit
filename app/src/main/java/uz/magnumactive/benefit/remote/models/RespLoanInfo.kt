package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RespLoanInfo(
        @SerializedName("code") val code: Int?,
        @SerializedName("msg") val msg: String?,
        @SerializedName("responseBody") val responseBody: LoanBody?
)

@Parcelize
data class LoanBody(
        @SerializedName("close_date") val closeDate: String?,
        @SerializedName("dep_pime") val depPime: Double?,
        @SerializedName("graph_limit_amount") val graphLimitAmount: Int?,
        @SerializedName("graph_limit_n") val graphLimitN: String?,
        @SerializedName("graph_limit_n_amount") val graphLimitNAmount: Double?,
        @SerializedName("graph_per") val graphPer: String?,
        @SerializedName("graph_per_amount") val graphPerAmount: Double?,
        @SerializedName("per_arrears") val perArrears: Double?,
        @SerializedName("per_curr") val perCurr: Double?,
        @SerializedName("sum_loan") val sumLoan: Int?
) : Parcelable

