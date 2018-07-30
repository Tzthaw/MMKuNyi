package com.example.ptut.mm_kunyi.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.adapters.base.BaseRecyclerAdapter
import com.example.ptut.mm_kunyi.deligate.onTapJobList
import com.example.ptut.mm_kunyi.viewholders.JobListViewHolder
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.JobListVO

class JobListAdapters(context: Context,private val onTapJobList: onTapJobList):BaseRecyclerAdapter<JobListViewHolder,JobListVO>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<JobListVO> {
        val view:View=mLayoutInflator.inflate(R.layout.job_content_item,parent,false)
        return JobListViewHolder(view,onTapJobList)
    }
}