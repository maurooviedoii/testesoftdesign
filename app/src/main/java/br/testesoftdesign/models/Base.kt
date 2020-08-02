package br.testesoftdesign.models.v2


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Base(
    @SerializedName("code") val code: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("data") val data: Data?
) : Parcelable