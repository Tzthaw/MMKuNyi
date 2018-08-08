package com.example.ptut.mm_kunyi.activities

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.View
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity
import com.example.ptut.mm_kunyi.adapters.CommentAdapter
import com.example.ptut.mm_kunyi.mvp.presenters.CommentPresenter
import com.example.ptut.mm_kunyi.mvp.views.CommentView
import com.example.ptut.mm_kunyi.utils.AppConstants
import com.example.ptut.mm_kunyi.utils.EmptyError
import com.example.ptut.mm_kunyi.utils.Error
import com.example.ptut.mm_kunyi.vos.CommentVO
import com.example.ptut.mm_kunyi.vos.JobListVO
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.comment_view_pod.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.view_comment_layout.*

class CommentActivity : BaseActivity(), CommentView, View.OnClickListener {
    private lateinit var commentPresenter: CommentPresenter
    private lateinit var commentAdapter: CommentAdapter
    private var jobListVO: JobListVO? = null
    private var jobId: Int? = null
    private var userId: Int? = null
    private var mProgressDialog: ProgressDialog? = null

    companion object {
        fun newIntent(jobId: Int, context: Context): Intent {
            val intent = Intent(context, CommentActivity::class.java)
            intent.putExtra(AppConstants.JOB_ID, jobId)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_comment_layout)

        jobId = intent.getIntExtra(AppConstants.JOB_ID, 0)

        commentPresenter = ViewModelProviders.of(this).get(CommentPresenter::class.java)
        commentPresenter.initPresenter(this)
        onSetupAdapter()
        commentPresenter.onNotifyJob("$jobId").observe(this, Observer<JobListVO> {
            dismissProgressDialog()
            commentEdit.setText("")
            jobListVO = it as JobListVO
            userId = jobListVO!!.comment!!.size
            commentAdapter.setNewData(jobListVO!!.comment as MutableList<CommentVO>)
        })
        commentPresenter.errorLD.observe(this, this)
        btnSendMessage.setOnClickListener(this)
    }

    fun onSetupAdapter() {
        commentRecycler.layoutManager = LinearLayoutManager(applicationContext)
        commentAdapter = CommentAdapter(this)
        commentRecycler.adapter = commentAdapter
        commentRecycler.setEmptyView(commentEmptyLayout)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSendMessage -> {

                if (commentEdit.text.isNotEmpty()) {
                    showProgressDialogInfinite("")
                    commentPresenter.onNotifySendComment("$jobId", "$userId",
                            commentEdit.text.toString())

                } else {
                    Snackbar.make(commentRootLayout, "Comment is Not Empty", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onChanged(error: Error?) {
        error?.let {
            when (it) {
                is EmptyError -> {
                    jobRecycler.setEmptyView(jobListEmpty)
                }

            }
        }
    }

    private fun showProgressDialogInfinite(msg: String) {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog!!.setMessage(Html.fromHtml(msg))
        if (!mProgressDialog!!.isShowing) {
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.show()
        }
    }

    fun dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }


}