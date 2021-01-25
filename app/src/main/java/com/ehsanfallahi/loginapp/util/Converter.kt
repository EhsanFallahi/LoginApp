package com.ehsanfallahi.loginapp.util

import androidx.room.TypeConverter
import com.ehsanfallahi.loginapp.data.dataModel.Data
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object Converter {
    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<Data>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Data::class.java)
        val adapter = moshi.adapter<List<Data>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(objects: List<Data>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Data::class.java)
        val adapter = moshi.adapter<List<Data>>(type)
        return adapter.toJson(objects)
    }
}