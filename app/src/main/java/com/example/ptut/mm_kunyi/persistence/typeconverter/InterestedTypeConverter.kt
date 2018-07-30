package com.example.ptut.mm_kunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter
import com.example.ptut.mm_kunyi.vos.InterestedVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InterestedTypeConverter{
    @TypeConverter
    fun toString(interestedList: List<InterestedVO>):String{
        return Gson().toJson(interestedList)
    }

    @TypeConverter
    fun toInterested(interestedJSon:String):List<InterestedVO>{
        val listType=object : TypeToken<List<InterestedVO>>(){}.type
        return Gson().fromJson(interestedJSon,listType)
    }
}