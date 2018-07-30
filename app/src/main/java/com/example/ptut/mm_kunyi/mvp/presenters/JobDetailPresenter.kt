package com.example.ptut.mm_kunyi.mvp.presenters

import android.arch.lifecycle.LiveData
import com.example.ptut.mm_kunyi.models.JobListModel
import com.example.ptut.mm_kunyi.mvp.views.JobDetailView
import com.example.ptut.mm_kunyi.vos.JobListVO

class JobDetailPresenter:BasePresenter<JobDetailView>(){
    override fun initPresenter(mView: JobDetailView) {
        super.initPresenter(mView)
    }
    fun onJobById(jobId:Int): LiveData<JobListVO> {
        return JobListModel.getInstance().getJobById(jobId)
    }
}