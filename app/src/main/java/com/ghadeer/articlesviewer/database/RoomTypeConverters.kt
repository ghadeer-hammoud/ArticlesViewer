package com.ghadeer.articlesviewer.database

import androidx.room.TypeConverter
import com.ghadeer.articlesviewer.data.models.Media
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class RoomTypeConverters {

    @TypeConverter
    fun stringListToString(stringList: List<String>): String? {
        return stringList.joinToString(";;")
    }

    @TypeConverter
    fun stringToStringList(string: String?): List<String> {
        return string?.split(";;") ?: listOf()
    }

    @TypeConverter
    fun mediaListToString(mediaList: List<Media>): String? {
        return Gson().toJson(mediaList)
    }

    @TypeConverter
    fun stringToMediaList(string: String?): List<Media> {
        val type: Type = object : TypeToken<List<Media>>() {}.type
        return Gson().fromJson(string, type)
    }

}