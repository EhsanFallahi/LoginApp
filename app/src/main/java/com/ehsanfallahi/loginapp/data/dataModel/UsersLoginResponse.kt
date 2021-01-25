package com.ehsanfallahi.loginapp.data.dataModel


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "users_table")
data class UsersLoginResponse(
    @PrimaryKey(autoGenerate = false)
    var pk:Int=0,
    @SerializedName("data")
    var data: List<Data>,
    @SerializedName("page")
    var page: Int,
    @SerializedName("per_page")
    var per_page: Int,
    @SerializedName("support")
    @Embedded(prefix = "_support")
    var support: Support,
    @SerializedName("total")
    var total: Int,
    @SerializedName("total_pages")
    var total_pages: Int
)