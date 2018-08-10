package com.example.ptut.mm_kunyi.persistence.daos

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data:T):Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data:List<T>): LongArray
    @Update
    fun update(data: T)
    @Delete
    fun delete(data:T)
}