package com.ehsanfallahi.loginapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ehsanfallahi.loginapp.data.dataModel.UsersLoginResponse
import com.ehsanfallahi.loginapp.data.database.UsersLoginDao
import com.ehsanfallahi.loginapp.data.remoteData.RemoteData
import com.ehsanfallahi.loginapp.util.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteData: RemoteData,
    private val usersLoginDao: UsersLoginDao
) {
    init {
        remoteData.getAllUser.observeForever{ usersDownloaded->saveUsersToDb(usersDownloaded)}

    }

    suspend fun getUsers(): LiveData<UsersLoginResponse> {
        return withContext(Dispatchers.IO){
            initUserData()
            return@withContext usersLoginDao.getAllUsers()
        }

    }

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
        remoteData.getAllNews()
    }

    private fun isFetchUser():Boolean{
        return true
    }
}