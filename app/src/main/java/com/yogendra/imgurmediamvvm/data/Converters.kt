package com.yogendra.imgurmediamvvm.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yogendra.imgurmediamvvm.model.PostImages
import com.yogendra.imgurmediamvvm.model.PostsData

open class Converters {

    @TypeConverter
    fun fromStringToPostsImages(value: String?): List<PostImages?>? {
        val listType = object : TypeToken<List<PostImages?>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListPostsImagesToString(list: List<PostImages?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun fromStringToListPostsData(value: String?): List<PostsData?> {
        val listType = object : TypeToken<List<PostsData?>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListPostsData(list: List<PostsData?>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}