package com.example.ptut.mm_kunyi.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.adapters.base.BaseRecyclerAdapter
import com.example.ptut.mm_kunyi.viewholders.ApplicantViewHolder
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.*

class ApplicantAdapter(context: Context):BaseRecyclerAdapter<ApplicantViewHolder,ApplicantVO>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ApplicantVO> {
        val view:View= mLayoutInflator.inflate(R.layout.content_applicant,parent,false)
        return ApplicantViewHolder(view)
    }
}