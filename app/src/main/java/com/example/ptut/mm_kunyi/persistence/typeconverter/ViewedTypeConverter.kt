package com.example.ptut.mm_kunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter
import com.example.ptut.mm_kunyi.vos.ViewedVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ViewedTypeConverter{
    @TypeConverter
    fun toString(viewedList: List<ViewedVO>):String{
        return Gson().toJson(viewedList)
    }

    @TypeConverter
    fun toViewed(viewedJSon:String):List<ViewedVO>{
        val listType=object : TypeToken<List<ViewedVO>>(){}.type
        return Gson().fromJson(viewedJSon,listType)
    }
}