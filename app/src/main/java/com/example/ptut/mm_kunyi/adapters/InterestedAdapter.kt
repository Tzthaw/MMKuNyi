package com.example.ptut.mm_kunyi.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.adapters.base.BaseRecyclerAdapter
import com.example.ptut.mm_kunyi.viewholders.InterestedViewHolder
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.InterestedVO

class InterestedAdapter(context: Context):BaseRecyclerAdapter<InterestedViewHolder,InterestedVO>(context){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<InterestedVO> {
        val view:View=mLayoutInflator.inflate(R.layout.content_applicant,parent,false)
        return InterestedViewHolder(view)
    }
}