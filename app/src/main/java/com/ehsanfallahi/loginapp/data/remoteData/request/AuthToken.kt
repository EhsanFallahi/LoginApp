package com.ehsanfallahi.loginapp.data.remoteData.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthToken(

    @Json(name = "token")
    val token: String? = null,

    @Json(name = "error")
    val error: String? = null
)
