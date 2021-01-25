package com.ehsanfallahi.loginapp.data.dataModel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Data(
    @SerializedName("avatar")
    var avatar: String,
    @SerializedName( "email")
    var email: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("last_name")
    var lastName: String
): Parcelable