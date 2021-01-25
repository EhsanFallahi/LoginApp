package com.ehsanfallahi.loginapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ehsanfallahi.loginapp.data.dataModel.UsersLoginResponse
import com.ehsanfallahi.loginapp.util.Converter

@Database(entities = [UsersLoginResponse::class],version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class UsersDatabase:RoomDatabase() {
    abstract val usersLoginDao:UsersLoginDao
    companion object{
        @Volatile
        private var INSTANCE:UsersDatabase?=null

        fun getInstance(context: Context):UsersDatabase{
            synchronized(this){
                var instance= INSTANCE

                if (instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java,
                        "users_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE=instance
                }
                return instance
            }
        }
    }
}