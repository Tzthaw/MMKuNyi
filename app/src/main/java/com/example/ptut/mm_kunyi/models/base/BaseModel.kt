package com.example.ptut.mm_kunyi.models.base

import android.content.Context
import com.example.ptut.mm_kunyi.persistence.AppDatabase


abstract class BaseModel protected constructor(context: Context) {
    protected var mTheDB: AppDatabase = AppDatabase.getDatabase(context)

}