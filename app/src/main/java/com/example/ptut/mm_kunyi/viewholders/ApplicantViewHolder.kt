package com.example.ptut.mm_kunyi.viewholders

import android.view.View
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.ApplicantVO
import kotlinx.android.synthetic.main.content_applicant.view.*

class ApplicantViewHolder(itemView:View):BaseViewHolder<ApplicantVO>(itemView) {
    override fun setData(data: ApplicantVO) {
        itemView.seekerImage.background=itemView.resources.getDrawable(R.color.greensea)
        itemView.seekerName.text=data.seekerName
    }

    override fun onClick(v: View?) {

    }
}