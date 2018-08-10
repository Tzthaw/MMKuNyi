package com.example.ptut.mm_kunyi.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity
import com.example.ptut.mm_kunyi.models.JobListModel
import com.example.ptut.mm_kunyi.utils.AppConstants
import com.example.ptut.mm_kunyi.vos.JobDurationVO
import com.example.ptut.mm_kunyi.vos.JobListVO
import com.example.ptut.mm_kunyi.vos.OfferAmount
import com.example.ptut.mm_kunyi.vos.RequiredSkillVO
import kotlinx.android.synthetic.main.activity_post_job.*
import kotlinx.android.synthetic.main.content_post_job.*

class PostJobActivity:BaseActivity(), View.OnClickListener {
    private var jobId:Int?=null
    companion object {
        fun newIntent(context: Context,jobId:Int): Intent {
            val intent= Intent(context, PostJobActivity::class.java)
            intent.putExtra(AppConstants.JOB_ID,jobId)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_job)
        toolbar.title=resources.getString(R.string.app_name)
        setSupportActionBar(toolbar)
        if(supportActionBar!=null){
            supportActionBar!!.title=""
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
           onBackPressed()
        }

        jobId=intent.getIntExtra(AppConstants.JOB_ID,0)
        newPostBtn.setOnClickListener(this)
    }



    private fun getData(id:String):JobListVO{
        val jobListVO=JobListVO()
        jobListVO.jobPostId=id.toInt()
        jobListVO.images= listOf("abc","abc","abc")
        jobListVO.email=emailEdit.text.toString()
        jobListVO.postedDate=postedEdit.text.toString()
        jobListVO.postClosedDate=postCloseEdit.text.toString()
        jobListVO.shortDesc=shortEdit.text.toString()
        jobListVO.fullDesc=fullEdit.text.toString()
        jobListVO.offerAmount= OfferAmount(OfferDurationEdit.text.toString(),
                AmountEdit.text.toString().toInt())
        jobListVO.jobDuration=JobDurationVO("",
                "",hourPerDayEdit.text.toString().toInt(),dayPerWeekEdit.text.toString().toInt(),
                totalworkEdit.text.toString().toInt())
        jobListVO.requiredSkill= listOf(RequiredSkillVO(requiredEdit.text.toString(),id.toInt()))
        jobListVO.phoneNo=phoneEdit.text.toString()
        jobListVO.importantNotes= listOf(noteEdit.text.toString())
        jobListVO.location=locationEdit.text.toString()
        return jobListVO
    }

    private fun alertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Discard Post?")
        builder.setMessage("The content will be all deleted")
        builder.setPositiveButton("Do It!") { dialog, which ->
            onBackPressed()
            finish()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }
    override fun onClick(v: View?) {
        JobListModel.getInstance().addPost("${jobId!!.minus(1)}",getData("${jobId}"),
                object :JobListModel.SetValueCallBack{
                    override fun onApplySuccess(msg: String) {
                        Snackbar.make(postLayout,msg,Snackbar.LENGTH_SHORT).show()
                    }

                })
    }

}