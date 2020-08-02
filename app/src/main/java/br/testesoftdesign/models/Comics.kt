package br.testesoftdesign.models.v2

import android.os.Parcelable
import br.testesoftdesign.models.Items
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comics(
    @SerializedName("items") val items: List<Items>
) : Parcelable