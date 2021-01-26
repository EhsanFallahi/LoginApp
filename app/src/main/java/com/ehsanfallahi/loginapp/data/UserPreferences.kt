package com.ehsanfallahi.loginapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.ehsanfallahi.loginapp.util.Constant.Companion.DATA_STORE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences @Inject constructor(context: Context){

    private val appContext=context.applicationContext
    private val dataStore=appContext.createDataStore(DATA_STORE_NAME)

    val fetchToke:Flow<String?>
    get()=dataStore.data.map {
        it[KEY_AUTH]
    }


    suspend fun saveAuthToken(email:String){
        dataStore.edit {
            it[KEY_AUTH]=email
        }
    }

    companion object{
        private val KEY_AUTH= stringPreferencesKey("key_auth")
    }
}