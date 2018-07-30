package com.example.ptut.mm_kunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter
import com.example.ptut.mm_kunyi.vos.ApplicantVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ApplicantTypeConverter{
    @TypeConverter
    fun toString(favoriteActions: List<ApplicantVO>):String{
       return Gson().toJson(favoriteActions)
    }
    @TypeConverter
    fun toApplicantActions(ApplicantJson:String):List<ApplicantVO>{
        val listType=object : TypeToken<List<ApplicantVO>>(){}.type
        return Gson().fromJson(ApplicantJson,listType)
    }
}