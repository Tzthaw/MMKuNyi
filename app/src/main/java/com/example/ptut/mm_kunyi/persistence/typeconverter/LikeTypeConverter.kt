package com.example.ptut.mm_kunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter
import com.example.ptut.mm_kunyi.vos.LikeVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LikeTypeConverter{
    @TypeConverter
    fun toString(relevantList: List<LikeVO>):String{
        return Gson().toJson(relevantList)
    }

    @TypeConverter
    fun toRelevant(relevantVOJson:String):List<LikeVO>{
        val listType=object : TypeToken<List<LikeVO>>(){}.type
        return Gson().fromJson(relevantVOJson,listType)
    }
}