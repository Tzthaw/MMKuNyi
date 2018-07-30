package com.example.ptut.mm_kunyi.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.ptut.mm_kunyi.persistence.daos.JobListDao
import com.example.ptut.mm_kunyi.utils.AppConstants
import com.example.ptut.mm_kunyi.vos.JobListVO

@Database(entities = [(JobListVO::class)], version =1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobListDao(): JobListDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.DB_NAME)
                        .allowMainThreadQueries()
                        .build()
            }
            val i = INSTANCE
            return i!!
        }
    }
}