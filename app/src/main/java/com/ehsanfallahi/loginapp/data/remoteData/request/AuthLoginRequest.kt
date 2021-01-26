package com.ehsanfallahi.loginapp.data.remoteData.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthLoginRequest(
    val email: String,
    val password: String
)

