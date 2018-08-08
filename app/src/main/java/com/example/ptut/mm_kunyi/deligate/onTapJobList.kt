package com.example.ptut.mm_kunyi.deligate

import com.example.ptut.mm_kunyi.vos.JobListVO

interface onTapJobList {
    fun onNotifyJobList(jobListVO: JobListVO)
    fun onNotifyLike(jobId:String,likeId:Int)
    fun onNotifyComment(jobId: String)
}