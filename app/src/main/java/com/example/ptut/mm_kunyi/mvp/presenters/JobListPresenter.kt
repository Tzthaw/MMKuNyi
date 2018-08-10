package com.example.ptut.mm_kunyi.mvp.presenters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.ptut.mm_kunyi.deligate.onTapJobList
import com.example.ptut.mm_kunyi.models.JobListModel
import com.example.ptut.mm_kunyi.mvp.views.JobListView
import com.example.ptut.mm_kunyi.vos.JobListVO
import com.google.firebase.auth.FirebaseUser

class JobListPresenter : BasePresenter<JobListView>(), onTapJobList {
    override fun initPresenter(mView: JobListView) {
        super.initPresenter(mView)
    }

    fun onNotifySetup() {
        JobListModel.getInstance().getJobListData( errorLD)
    }

    fun onForceRefresh(){
        JobListModel.getInstance().getJobListData(errorLD)
    }

    fun onNotifyTapLike(jobId: String,likeId:String){
        JobListModel.getInstance().addLike(jobId,likeId)
    }


    fun onNotifyJobListData():LiveData<List<JobListVO>>{
        return JobListModel.getInstance().getJobList()
    }

    override fun onNotifyLike(jobId: String, likeId: Int) {
        mView.setLikeCount(jobId,likeId)
    }
    override fun onNotifyJobList(jobListVO: JobListVO) {
        mView.lunchJobDetail(jobListVO)
    }
    override fun onNotifyComment(jobId: String) {
        mView.tapComment(jobId)
    }

}