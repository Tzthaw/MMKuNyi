package com.example.ptut.mm_kunyi.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.ptut.healthcare.component.EmptyViewPod
import com.example.ptut.mm_kunyi.KuNyiApp
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity
import com.example.ptut.mm_kunyi.adapters.JobListAdapters
import com.example.ptut.mm_kunyi.components.pods.UserAccountViewPod
import com.example.ptut.mm_kunyi.deligate.UserController
import com.example.ptut.mm_kunyi.models.JobListModel
import com.example.ptut.mm_kunyi.mvp.presenters.JobListPresenter
import com.example.ptut.mm_kunyi.mvp.views.JobListView
import com.example.ptut.mm_kunyi.utils.*
import com.example.ptut.mm_kunyi.vos.JobListVO
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseUser
import com.padcmyanmar.mmnews.kotlin.components.SmartScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.view_comment_layout.*


class JobListActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, JobListView,
        GoogleApiClient.OnConnectionFailedListener, UserController {


    private lateinit var jobListPresenter: JobListPresenter
    private lateinit var jobListAdapter: JobListAdapters
    private var mSmartScrollListener: SmartScrollListener? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var userAccountViewPod:UserAccountViewPod?=null
    private var jobId:Int?=null
    companion object {
        private val IE_NOTIFICATION_ID = "IE_NOTIFICATION_ID"
        private val IE_LAUNCH_ACTION = "IE_LAUNCH_ACTION"
        private val LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION = 2222
        private val LAUNCH_ACTION_TAP_NOTI_BODY = 2223

        fun newIntent(context: Context): Intent {
            return Intent(context, JobListActivity::class.java)
        }

        fun newIntentSaveNews(context: Context, notificationId: Int): Intent {
            val intent = Intent(context, JobListActivity::class.java)
            intent.putExtra(IE_NOTIFICATION_ID, notificationId)
            intent.putExtra(IE_LAUNCH_ACTION, LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION)
            return intent
        }

        fun newIntentNotiBody(context: Context): Intent {
            val intent = Intent(context, JobListActivity::class.java)
            intent.putExtra(IE_LAUNCH_ACTION, LAUNCH_ACTION_TAP_NOTI_BODY)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        JobListModel.getInstance()
        setUpUiComponent()
        navigateTool()
        refreshFunction()
        googleAuthenticate()
        jobListPresenter.errorLD.observe(this, this)
        jobListPresenter.onNotifyJobListData().observe(this, Observer<List<JobListVO>> {
            swipeRefreshLayout.isRefreshing = false
            jobId=it!!.size
            jobListAdapter.setNewData(it as MutableList<JobListVO>)
        })


    }

    private fun setUpUiComponent() {
        swipeRefreshLayout.isRefreshing = true
        jobListPresenter = ViewModelProviders.of(this).get(JobListPresenter::class.java)
        jobListPresenter.initPresenter(this)
        jobListPresenter.onNotifySetup()
        jobRecycler.layoutManager = LinearLayoutManager(this)
        jobListAdapter = JobListAdapters(applicationContext, jobListPresenter)
        jobRecycler.adapter = jobListAdapter
    }

    private fun navigateTool() {
        fab.setOnClickListener {
            if (mAuth.currentUser!=null) {
                startPostJobActivity()
            } else {
                Snackbar.make(jobRecycler,
                        "Please Sign in with Your google account", Snackbar.LENGTH_LONG).show()
            }
        }
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        userAccountViewPod=nav_view.getHeaderView(0) as UserAccountViewPod
        userAccountViewPod!!.refreshUserLoginStatus()
        userAccountViewPod!!.setUserController(this)
    }


    private fun refreshFunction() {
        mSmartScrollListener = SmartScrollListener(object : SmartScrollListener.OnSmartScrollListener {
            override fun onListEndReach() {
                Snackbar.make(jobRecycler, "Loading More Data", Snackbar.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = true
                jobListPresenter.onForceRefresh()
            }
        })
        jobRecycler.addOnScrollListener(mSmartScrollListener)
        swipeRefreshLayout.setOnRefreshListener {
            jobListPresenter.onNotifySetup()
        }
    }

    private fun startPostJobActivity() {
        startActivity(PostJobActivity.newIntent(applicationContext,jobId!!+1))
    }

    private fun googleAuthenticate() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(AppConstants.requestIdToken)
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()
    }

    private fun processGoogleSignInResult(signInResult: GoogleSignInResult) {
        if (signInResult.isSuccess) {
            val account = signInResult.signInAccount
            JobListModel.getInstance().authenticateUserWithGoogleAccount(account!!, object : JobListModel.SignInWithGoogleAccountDelegate {
                override fun onSuccessSignIn(signInAccount: GoogleSignInAccount) {
                    userAccountViewPod!!.refreshUserLoginStatus()
                }

                override fun onFailureSignIn(msg: String) {
                    EmptyError(msg)
                }
            })
        } else {
            Snackbar.make(jobRecycler, "Google sign-in failed.", Snackbar.LENGTH_LONG).show()
        }
    }
    private fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, AppConstants.RC_GOOGLE_SIGN_IN)
    }

    private fun sendInvitation() {
        val intent = AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build()
        startActivityForResult(intent, AppConstants.RC_INVITE_TO_APP)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.actionInvite ->{
                if(mFirebaseUser!=null){
                    sendInvitation()
                }else{
                    Snackbar.make(rootLayout,"please sign in your goog account",Snackbar.LENGTH_LONG).show()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.RC_GOOGLE_SIGN_IN) {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                processGoogleSignInResult(result)
            }else{
                // Check how many invitations were sent and log.
                val ids = AppInviteInvitation.getInvitationIds(resultCode, data)
                Log.d(KuNyiApp.TAG, "Invitations sent: " + ids.size)
                Snackbar.make(rootLayout, "Invitations sent to " + ids.size + " friends", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {}

    override fun setLikeCount(jobId: String, likeId: Int) {
        jobListPresenter.onNotifyTapLike(jobId,"$likeId" )
    }
    override fun lunchJobDetail(jobListVO: JobListVO) {
        startActivity(JobDetailActivity.newIntent(applicationContext, jobListVO.jobPostId!!))
    }
    override fun tapComment(jobId: String) {
        startActivity(CommentActivity.newIntent(jobId.toInt(),applicationContext))
    }
    override fun onChanged(error: Error?) {
        error?.let {
            when (it) {
                is EmptyError -> {
                    val jobListEmptyError:EmptyViewPod = jobListEmpty as EmptyViewPod
                    jobListEmptyError.setEmptyData("Empty Data")
                    jobRecycler.setEmptyView(jobListEmptyError)
                }
                is DatabaseError ->
                    Snackbar.make(rootLayout, "Database Reference Error", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    override fun onTapSignOut() {
        mAuth.signOut()
        userAccountViewPod!!.refreshUserLoginStatus()
    }

    override fun onTapLogin() {
        signInWithGoogle()
    }

}
