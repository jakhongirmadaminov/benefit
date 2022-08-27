package uz.magnumactive.benefit.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResultDTO(
    @SerializedName("result") val result: List<MarketProductDTO>
) : Parcelable
