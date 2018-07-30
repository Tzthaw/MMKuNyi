package com.example.ptut.mm_kunyi.mvp.presenters

import android.arch.lifecycle.MutableLiveData
import com.example.ptut.mm_kunyi.deligate.onTapJobList
import com.example.ptut.mm_kunyi.models.JobListModel
import com.example.ptut.mm_kunyi.mvp.views.JobListView
import com.example.ptut.mm_kunyi.vos.JobListVO

class JobListPresenter:BasePresenter<JobListView>(),onTapJobList {
    private lateinit var mJobListLD:MutableLiveData<List<JobListVO>>
    override fun initPresenter(mView: JobListView) {
        super.initPresenter(mView)
        mJobListLD= MutableLiveData()
    }

    fun onNotifySetup(){
        JobListModel.getInstance().getJobListData(mJobListLD,errorLD)
    }

    override fun tapJobList(jobListVO: JobListVO) {
        mView.lunchJobDetail(jobListVO)
    }

    var jobListLD:MutableLiveData<List<JobListVO>>? = null
        get() = mJobListLD
}