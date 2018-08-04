package com.example.ptut.mm_kunyi.activities

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity
import com.example.ptut.mm_kunyi.adapters.ApplicantAdapter
import com.example.ptut.mm_kunyi.adapters.InterestedAdapter
import com.example.ptut.mm_kunyi.adapters.RelevantAdapter
import com.example.ptut.mm_kunyi.adapters.ViewedAdapter
import com.example.ptut.mm_kunyi.mvp.presenters.JobDetailPresenter
import com.example.ptut.mm_kunyi.mvp.views.JobDetailView
import com.example.ptut.mm_kunyi.utils.AppConstants
import com.example.ptut.mm_kunyi.vos.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.content_job_detail.*
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("Registered")
class JobDetailActivity : BaseActivity(), JobDetailView, View.OnClickListener {

    private lateinit var jobListPresenter: JobDetailPresenter
    private lateinit var applicantAdapter: ApplicantAdapter
    private lateinit var interestedAdapter: InterestedAdapter
    private lateinit var viewedAdapter: ViewedAdapter
    private lateinit var relevantAdapter: RelevantAdapter
    private lateinit var mDatabase: DatabaseReference
    private var mFirebaseUser: FirebaseUser? = null
    private var jobListVO: JobListVO? = null

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

        val id: Int = intent.getIntExtra(AppConstants.JOB_ID, 0)

        jobListPresenter = ViewModelProviders.of(this).get(JobDetailPresenter::class.java)
        jobListPresenter.initPresenter(this)
        jobListPresenter.onNotifyJobById(id).observe(this, Observer<JobListVO> {
            jobListVO = it
            setUpUiComponent(it as JobListVO)
        })

    }

    @SuppressLint("SetTextI18n")
    private fun setUpUiComponent(jobListVO: JobListVO) {
        //set data initial
        shortDescription.text = jobListVO.shortDesc
        fullDescription.text = jobListVO.fullDesc
        emailText.text = jobListVO.email
        availableText.text = "Available-${jobListVO.availablePostCount}"
        amountText.text = "${jobListVO.offerAmount!!.amount}"
        postDateText.text = "${jobListVO.postedDate} to ${jobListVO.postClosedDate}"
        phNoText.text = jobListVO.phoneNo
        ratingText.text = "${jobListVO.makeMoneyRating}"
        locationText.text = jobListVO.location
        val lparams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        //set RequiredSkill Layout data
        requiredSkill(lparams, jobListVO)
        //set JobDuration Layout data
        jobDuration(jobListVO)
        applicantView(jobListVO)
        interestedView(jobListVO)
        viewed(jobListVO)
        relevantView(jobListVO)
        apply.setOnClickListener(this)
    }

    private fun requiredSkill(lparams: LinearLayout.LayoutParams, jobListVO: JobListVO) {
        lparams.setMargins(4, 8, 4, 0);
        for (requiredSkill in jobListVO.requiredSkill!!) {
            addTextView(requiredLayout, lparams, requiredSkill)
        }
    }

    private fun jobDuration(jobListVO: JobListVO) {
        startDate.text = jobListVO.jobDuration?.jobStartDate
        endDate.text = jobListVO.jobDuration?.jobEndDate
        totalDate.text = "${jobListVO.jobDuration?.totalWorkingDays}"
        workingDay.text = "${jobListVO.jobDuration?.workingDaysPerWeek}"
        workHour.text = "${jobListVO.jobDuration?.workingHoursPerDay}"
    }

    private fun addTextView(layout: LinearLayout, lparams: LinearLayout.LayoutParams, requiredSkillVO: RequiredSkillVO) {
        val tv = TextView(this)
        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot, 0, 0, 0)
        tv.compoundDrawablePadding = 8;
        tv.layoutParams = lparams
        tv.text = requiredSkillVO.skillName
        tv.setPadding(4, 8, 8, 8)
        layout.addView(tv)
    }

    private fun applicantView(jobListVO: JobListVO) {
        val applicantLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        applicantRecycler.layoutManager = applicantLayout
        applicantAdapter = ApplicantAdapter(applicationContext)
        applicantRecycler.adapter = applicantAdapter
        applicantAdapter.setNewData(jobListVO.applicant as MutableList<ApplicantVO>)
    }

    private fun interestedView(jobListVO: JobListVO) {
        val applicantLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        interestedRecycler.layoutManager = applicantLayout
        interestedAdapter = InterestedAdapter(applicationContext)
        interestedRecycler.adapter = interestedAdapter
        interestedAdapter.setNewData(jobListVO.interested as MutableList<InterestedVO>)
    }

    private fun viewed(jobListVO: JobListVO) {
        val applicantLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewedRecycler.layoutManager = applicantLayout
        viewedAdapter = ViewedAdapter(applicationContext)
        viewedRecycler.adapter = viewedAdapter
        viewedAdapter.setNewData(jobListVO.viewed as MutableList<ViewedVO>)
    }

    private fun relevantView(jobListVO: JobListVO) {
        val applicantLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        relevantRecycler.layoutManager = applicantLayout
        relevantAdapter = RelevantAdapter(applicationContext)
        relevantRecycler.adapter = relevantAdapter
        relevantAdapter.setNewData(jobListVO.relevant as MutableList<RelevantVO>)
    }

    override fun onStart() {
        super.onStart()
        mDatabase = FirebaseDatabase.getInstance().reference
        mFirebaseUser = mAuth.currentUser
    }

    override fun onClick(v: View?) {
//        jobListPresenter.onNotifyApply(jobListVO!!.jobId!!)

    }


}