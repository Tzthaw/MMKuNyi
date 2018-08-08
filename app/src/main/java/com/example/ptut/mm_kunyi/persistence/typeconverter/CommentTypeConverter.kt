package com.example.ptut.mm_kunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter
import com.example.ptut.mm_kunyi.vos.CommentVO

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CommentTypeConverter{
    @TypeConverter
    fun toString(commentList: List<CommentVO>):String{
        return Gson().toJson(commentList)
    }

    @TypeConverter
    fun toComment(commentVOJson:String):List<CommentVO>{
        val listType=object : TypeToken<List<CommentVO>>(){}.type
        return Gson().fromJson(commentVOJson,listType)
    }
}