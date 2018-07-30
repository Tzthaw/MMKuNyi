package com.example.ptut.mm_kunyi.mvp.views

import com.example.ptut.mm_kunyi.vos.JobListVO

interface JobListView:BaseView {
    fun lunchJobDetail(jobListVO: JobListVO)
}