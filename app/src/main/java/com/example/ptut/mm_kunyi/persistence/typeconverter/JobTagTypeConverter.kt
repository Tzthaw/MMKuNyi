package com.example.ptut.mm_kunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter
import com.example.ptut.mm_kunyi.vos.JobTagsVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JobTagTypeConverter{
    @TypeConverter
    fun toString(jobTagList: List<JobTagsVO>):String{
        return Gson().toJson(jobTagList)
    }

    @TypeConverter
    fun toJobTags(jobTagJson:String):List<JobTagsVO>{
        val listType=object : TypeToken<List<JobTagsVO>>(){}.type
        return Gson().fromJson(jobTagJson,listType)
    }
}