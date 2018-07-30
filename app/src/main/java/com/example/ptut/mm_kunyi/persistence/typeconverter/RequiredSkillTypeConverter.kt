package com.example.ptut.mm_kunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter
import com.example.ptut.mm_kunyi.vos.RequiredSkillVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RequiredSkillTypeConverter{
    @TypeConverter
    fun toString(requiredskillList: List<RequiredSkillVO>):String{
        return Gson().toJson(requiredskillList)
    }

    @TypeConverter
    fun toRequiredSkill(requiredskillJSon:String):List<RequiredSkillVO>{
        val listType=object : TypeToken<List<RequiredSkillVO>>(){}.type
        return Gson().fromJson(requiredskillJSon,listType)
    }
}