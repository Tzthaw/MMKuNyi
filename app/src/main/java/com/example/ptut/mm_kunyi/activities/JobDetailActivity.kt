package com.example.ptut.mm_kunyi.activities

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity
import com.example.ptut.mm_kunyi.mvp.presenters.JobDetailPresenter
import com.example.ptut.mm_kunyi.mvp.presenters.JobListPresenter
import com.example.ptut.mm_kunyi.mvp.views.JobDetailView
import com.example.ptut.mm_kunyi.utils.AppConstants
import com.example.ptut.mm_kunyi.vos.JobListVO
import com.example.ptut.mm_kunyi.vos.RequiredSkillVO
import kotlinx.android.synthetic.main.content_job_detail.*
import java.util.ArrayList

@SuppressLint("Registered")
class JobDetailActivity:BaseActivity(),JobDetailView {
    private lateinit var jobListPresenter:JobDetailPresenter

    companion object {
        fun newIntent(context: Context, jobId: Int): Intent {
            val intent = Intent(context, JobDetailActivity::class.java)
            intent.putExtra(AppConstants.JOB_ID, jobId)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        val id:Int=intent.getIntExtra(AppConstants.JOB_ID,0)

        jobListPresenter=ViewModelProviders.of(this).get(JobDetailPresenter::class.java)
        jobListPresenter.initPresenter(this)
        jobListPresenter.onJobById(id).observe(this, Observer<JobListVO>{
            setUpUiComponent(it as JobListVO)
        })

    }
    @SuppressLint("SetTextI18n")
    private fun setUpUiComponent(jobListVO: JobListVO){
        shortDescription.text=jobListVO.shortDesc
        fullDescription.text=jobListVO.fullDesc
        emailText.text=jobListVO.email
        availableText.text="Available-${jobListVO.availablePostCount}"
        amountText.text="${jobListVO.offerAmount!!.amount}"
        postDateText.text="${jobListVO.postedDate} to ${jobListVO.postClosedDate}"
        phNoText.text=jobListVO.phoneNo
        ratingText.text="${jobListVO.makeMoneyRating}"
        locationText.text=jobListVO.location

        val lparams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lparams.setMargins(4,8,4,0);

        for(requiredSkill in jobListVO.requiredSkill!!){
           addTextView(requiredLayout,lparams,requiredSkill)
        }

    }

    private fun addTextView(layout:LinearLayout,lparams:LinearLayout.LayoutParams,requiredSkillVO: RequiredSkillVO){
        val tv = TextView(this)
        tv.background=resources.getDrawable(R.drawable.border_bg)
        tv.layoutParams = lparams
        tv.text=requiredSkillVO.skillName
        tv.setPadding(8,8,8,8)
        layout.addView(tv)
    }
}