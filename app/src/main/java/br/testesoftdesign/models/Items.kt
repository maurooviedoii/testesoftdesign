package br.testesoftdesign.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Items constructor(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String
) : Parcelable {
    override fun toString(): String {
        return name
    }
}
