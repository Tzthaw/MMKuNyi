package com.example.ptut.mm_kunyi.viewholders

import android.support.v7.view.menu.ActionMenuItemView
import android.view.View
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.BaseVO
import com.example.ptut.mm_kunyi.vos.InterestedVO
import com.example.ptut.mm_kunyi.vos.ViewedVO
import kotlinx.android.synthetic.main.content_applicant.view.*

class InterestedViewHolder(itemView:View):BaseViewHolder<InterestedVO>(itemView) {
    override fun setData(data: InterestedVO) {
        itemView.seekerImage.background=itemView.resources.getDrawable(R.color.orange)
       itemView.seekerName.text=data.seekerName
    }
    override fun onClick(v: View?) {

    }


}