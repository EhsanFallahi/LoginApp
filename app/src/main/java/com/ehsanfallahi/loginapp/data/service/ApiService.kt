package com.ehsanfallahi.loginapp.data.service

import com.ehsanfallahi.loginapp.data.dataModel.UsersLoginResponse
import com.ehsanfallahi.loginapp.util.ConnectivityInterceptor
import com.ehsanfallahi.loginapp.util.Constant.Companion.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    //https://reqres.in/api/users?page=2

    @GET("users/")
    fun getAllUsers():Deferred<UsersLoginResponse>
//
//    companion object{
//        operator fun invoke():ApiService{
//
//            val okHttpClient=OkHttpClient.Builder().build()
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(ApiService::class.java)
//        }
//    }
}