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

    override fun tapJobList(jobListVO: JobListVO) {
        mView.lunchJobDetail(jobListVO)
    }

    var jobListLD: LiveData<List<JobListVO>>? = null
        get() = JobListModel.getInstance().getJobList()

    fun isGoogleSignIn(): Boolean {
        return JobListModel.getInstance().isUserSignIn()
    }

    fun onNotifyLogin():FirebaseUser{
        return JobListModel.getInstance().getUserInfo()
    }
}