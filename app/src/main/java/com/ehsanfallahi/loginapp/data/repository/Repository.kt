package com.ehsanfallahi.loginapp.data.repository

import androidx.lifecycle.LiveData
import com.ehsanfallahi.loginapp.data.UserPreferences
import com.ehsanfallahi.loginapp.data.dataModel.Data
import com.ehsanfallahi.loginapp.data.dataModel.UsersLoginResponse
import com.ehsanfallahi.loginapp.data.database.UsersLoginDao
import com.ehsanfallahi.loginapp.data.remoteData.RemoteData
import com.ehsanfallahi.loginapp.data.remoteData.request.AuthLoginRequest
import kotlinx.coroutines.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteData: RemoteData,
    private val usersLoginDao: UsersLoginDao,
    private val preferences: UserPreferences
) {
    init {
        remoteData.getAllUser.observeForever{ usersDownloaded->saveUsersToDb(usersDownloaded)}

    }

    suspend fun getAllUsers(): LiveData<UsersLoginResponse> {
        return withContext(Dispatchers.IO){
            initUserData()
            return@withContext usersLoginDao.getAllUsers()
        }

    }

    suspend fun getUser(id:Int)=remoteData.getOneUser(id)

    private fun saveUsersToDb(userDownloaded: UsersLoginResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            usersLoginDao.insertUsers(userDownloaded!!)
        }
    }

    private suspend fun initUserData(){
        if(isFetchUser()){
            fetchUser()
        }
    }

    private suspend fun fetchUser(){
        remoteData.getAllUser()
    }

    private fun isFetchUser():Boolean{
        return true
    }

    suspend fun login(authLoginRequest: AuthLoginRequest)=remoteData.login(authLoginRequest)

    suspend fun saveEmailUser(email:String){
        preferences.saveEmailUser(email)
    }

    suspend fun clearPrefereces(){
        preferences.clearPref()
    }


}