package com.ehsanfallahi.loginapp.data.dataModel


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Support(
    @SerializedName("text")
    var text: String,
    @SerializedName("url")
    var url: String
)