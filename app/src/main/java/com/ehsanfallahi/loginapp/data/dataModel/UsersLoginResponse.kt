package com.ehsanfallahi.loginapp.data.dataModel


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsersLoginResponse(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("page")
    var page: Int,
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("support")
    var support: Support,
    @SerializedName("total")
    var total: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)