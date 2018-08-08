package com.example.ptut.mm_kunyi.mvp.presenters

import android.arch.lifecycle.LiveData
import com.example.ptut.mm_kunyi.models.JobListModel
import com.example.ptut.mm_kunyi.mvp.views.CommentView
import com.example.ptut.mm_kunyi.vos.CommentVO
import com.example.ptut.mm_kunyi.vos.JobListVO

class CommentPresenter :BasePresenter<CommentView>(){
    override fun initPresenter(mView: CommentView) {
        super.initPresenter(mView)
    }
    fun onNotifyJob(jobId:String):LiveData<JobListVO>{
        return JobListModel.getInstance().getJobByIdComment(jobId)
    }
    fun onNotifySendComment(jobId: String,userId:String,details:String){
        JobListModel.getInstance().addComment(jobId,userId,details)
    }
}