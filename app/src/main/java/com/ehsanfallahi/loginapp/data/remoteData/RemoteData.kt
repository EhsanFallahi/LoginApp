package com.ehsanfallahi.loginapp.data.remoteData

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ehsanfallahi.loginapp.data.dataModel.Data
import com.ehsanfallahi.loginapp.data.dataModel.UsersLoginResponse
import com.ehsanfallahi.loginapp.data.remoteData.request.AuthLoginRequest
import com.ehsanfallahi.loginapp.data.service.ApiService
import com.ehsanfallahi.loginapp.util.Constant.Companion.MY_TAG
import com.ehsanfallahi.loginapp.util.NoConnectivityException
import javax.inject.Inject

class RemoteData@Inject
constructor(private val apiService:ApiService) {

    private val _getAllUsers= MutableLiveData<UsersLoginResponse>()
    val getAllUser: LiveData<UsersLoginResponse>
        get() = _getAllUsers

    private val _getUser= MutableLiveData<Data>()
    val getUser: LiveData<Data>
        get() = _getUser

    suspend fun getAllUser(){
        try{
            val response=apiService.getAllUsers().await()
            _getAllUsers.postValue(response)
        }catch (e: NoConnectivityException){
            Log.e(MY_TAG,"No internet connection:${e.message}")
        }
    }

    suspend fun getOneUser(id:Int):Data{
        try{
           return apiService.getUser(id).await()
        }catch (e: NoConnectivityException){
            Log.e(MY_TAG,"No internet connection:${e.message}")
            return getUser.value!!
        }
    }


    suspend fun login(
        authLoginRequest: AuthLoginRequest
    )=apiService.login(authLoginRequest)
}
