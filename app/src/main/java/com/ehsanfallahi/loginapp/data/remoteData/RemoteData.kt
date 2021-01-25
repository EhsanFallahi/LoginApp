package com.ehsanfallahi.loginapp.data.remoteData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ehsanfallahi.loginapp.data.dataModel.UsersLoginResponse
import com.ehsanfallahi.loginapp.data.service.ApiService
import com.ehsanfallahi.loginapp.util.Constant.Companion.MY_TAG
import com.ehsanfallahi.loginapp.util.NoConnectivityException
import javax.inject.Inject

class RemoteData@Inject
constructor(private val apiService:ApiService) {

    private val _getAllUsers= MutableLiveData<UsersLoginResponse>()
    val getAllUser: LiveData<UsersLoginResponse>
        get() = _getAllUsers

    suspend fun getAllNews(){
        try{
            val response=apiService.getAllUsers().await()
            _getAllUsers.postValue(response)
        }catch (e: NoConnectivityException){
            Log.e(MY_TAG,"No internet connection:${e.message}")
        }
    }

}
