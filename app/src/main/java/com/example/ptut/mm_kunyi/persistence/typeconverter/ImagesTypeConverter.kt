package com.example.ptut.mm_kunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter

class ImagesTypeConverter {

    @TypeConverter
    fun toStringList(imagesCommaSeparated: String): List<String> {
        return imagesCommaSeparated.split(",".toRegex())
    }

    @TypeConverter
    fun toString(imageList: List<String>): String {
        val stringBuilder = StringBuilder()
        for (image in imageList) {
            stringBuilder.append(image).append(",")
        }
        if (stringBuilder.isNotEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
        }
        return stringBuilder.toString()
    }
}