package com.example.ptut.mm_kunyi.persistence.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.ptut.mm_kunyi.vos.CommentVO
import com.example.ptut.mm_kunyi.vos.JobListVO

@Dao
interface JobListDao:BaseDao<JobListVO> {
    @Query("SELECT * FROM jobList")
    fun getJobList():LiveData<List<JobListVO>>

    @Query("SELECT * FROM jobList WHERE jobPostId=:jobPostId")
    fun getJobById(jobPostId:Int):LiveData<JobListVO>

    @Query("SELECT * FROM jobList WHERE jobId=:jobId")
    fun getJobByIdComment(jobId:String):LiveData<JobListVO>

    @Query("DELETE FROM jobList")
    fun deleteAll():Unit
}