package com.ehsanfallahi.loginapp.data.service

import com.ehsanfallahi.loginapp.data.dataModel.Data
import com.ehsanfallahi.loginapp.data.dataModel.UsersLoginResponse
import com.ehsanfallahi.loginapp.data.remoteData.request.AuthLoginRequest
import com.ehsanfallahi.loginapp.data.remoteData.request.AuthToken

import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {
    //https://reqres.in/api/users?per_page=12

    @GET("users/")
    fun getAllUsers():Deferred<UsersLoginResponse>


    @POST("login")
    suspend fun login(
        @Body loginRequest: AuthLoginRequest
    ): AuthToken

//https://reqres.in/api/users/1
    @GET("users/{id}")
    suspend fun getUser(
        @Path("id")id:Int
    ):Deferred<Data>
}