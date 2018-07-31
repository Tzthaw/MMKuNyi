package com.example.ptut.mm_kunyi.viewholders

import android.view.View
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.ApplicantVO
import com.example.ptut.mm_kunyi.vos.RelevantVO
import kotlinx.android.synthetic.main.content_applicant.view.*

class RelevantViewHolder(itemView:View):BaseViewHolder<RelevantVO>(itemView) {
    override fun setData(data: RelevantVO) {
        itemView.seekerImage.background=itemView.resources.getDrawable(R.color.pomergranate)
        itemView.seekerName.text=data.seekerName
    }
    override fun onClick(v: View?) {

    }
}