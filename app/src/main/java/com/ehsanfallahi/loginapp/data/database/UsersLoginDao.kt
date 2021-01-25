package com.ehsanfallahi.loginapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ehsanfallahi.loginapp.data.dataModel.UsersLoginResponse

@Dao
interface UsersLoginDao {

    @Query("SELECT * FROM users_table")
     fun getAllUsers():LiveData<UsersLoginResponse>

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertUsers(usersLoginResponse: UsersLoginResponse)
}