package com.example.ptut.mm_kunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter
import com.example.ptut.mm_kunyi.vos.RelevantVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RelevantTypeConverter{
    @TypeConverter
    fun toString(relevantList: List<RelevantVO>):String{
        return Gson().toJson(relevantList)
    }

    @TypeConverter
    fun toRelevant(relevantVOJson:String):List<RelevantVO>{
        val listType=object : TypeToken<List<RelevantVO>>(){}.type
        return Gson().fromJson(relevantVOJson,listType)
    }
}