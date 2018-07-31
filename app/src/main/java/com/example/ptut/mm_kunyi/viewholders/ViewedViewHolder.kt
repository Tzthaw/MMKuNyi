package com.example.ptut.mm_kunyi.viewholders

import android.view.View
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.ApplicantVO
import com.example.ptut.mm_kunyi.vos.ViewedVO
import kotlinx.android.synthetic.main.content_applicant.view.*

class ViewedViewHolder(itemView:View):BaseViewHolder<ViewedVO>(itemView) {
    override fun setData(data: ViewedVO) {
        itemView.seekerImage.background=itemView.resources.getDrawable(R.color.belizehole)
        itemView.seekerName.text=data.seekerName
    }

    override fun onClick(v: View?) {

    }
}