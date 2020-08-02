package br.testesoftdesign.models.v2

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName("offset") val offset: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("results") val results: List<Results>
) : Parcelable