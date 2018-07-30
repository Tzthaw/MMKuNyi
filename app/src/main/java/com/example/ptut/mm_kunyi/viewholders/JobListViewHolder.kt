package com.example.ptut.mm_kunyi.viewholders

import android.annotation.SuppressLint
import android.view.View
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.deligate.onTapJobList
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.JobListVO
import kotlinx.android.synthetic.main.job_content_item.view.*

class JobListViewHolder(itemView: View, private val tapJobList: onTapJobList) : BaseViewHolder<JobListVO>(itemView) {
    private lateinit var jobListVO: JobListVO
    @SuppressLint("SetTextI18n")
    override fun setData(data: JobListVO) {
        jobListVO=data
        itemView.shortDesc.text = data.shortDesc
        itemView.fullDesc.text = data.fullDesc
        itemView.email.text = data.email
        itemView.location.text = data.location
        itemView.amount.text = data.offerAmount!!.amount.toString()
        itemView.postDate.text = "${data.postedDate} to ${data.postClosedDate}"
        itemView.rating.text = data.makeMoneyRating.toString()
        itemView.applicant.text = "Applicant~${data.applicant!!.count()}"
        itemView.viewed.text="Viewed~${data.viewed!!.count()}"
        itemView.like.setOnClickListener(this)
        itemView.comment.setOnClickListener(this)
        itemView.share.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
//        when (v!!.id) {
//            R.id.like -> itemView.like.setImageResource(R.drawable.ic_like_s)
//
//        }
        tapJobList.tapJobList(jobListVO)

    }

}