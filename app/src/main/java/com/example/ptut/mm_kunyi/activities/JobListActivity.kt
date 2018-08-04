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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity
import com.example.ptut.mm_kunyi.adapters.JobListAdapters
import com.example.ptut.mm_kunyi.models.JobListModel
import com.example.ptut.mm_kunyi.mvp.presenters.JobListPresenter
import com.example.ptut.mm_kunyi.mvp.views.JobListView
import com.example.ptut.mm_kunyi.utils.*
import com.example.ptut.mm_kunyi.vos.JobListVO
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.padcmyanmar.mmnews.kotlin.components.SmartScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*


class JobListActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, JobListView,
        GoogleApiClient.OnConnectionFailedListener {
    private lateinit var jobListPresenter: JobListPresenter
    private lateinit var jobListAdapter: JobListAdapters
    private var mSmartScrollListener: SmartScrollListener? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, JobListActivity::class.java)
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
        jobListPresenter.jobListLD!!.observe(this, Observer<List<JobListVO>> {
            swipeRefreshLayout.isRefreshing = false
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
            if (jobListPresenter.isGoogleSignIn()) {
                startPostJobActivity()
            } else {
                Snackbar.make(jobRecycler, "Please Sign in with Your google account", Snackbar.LENGTH_LONG).setAction("Sign-In") { signInWithGoogle() }.show()
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    private fun navigateData(mFirebaseUser: FirebaseUser) {
        val header: View = nav_view.getHeaderView(0)
        val name = (header.findViewById(R.id.seekerUserName) as TextView)
        val email = (header.findViewById(R.id.seekerEmail) as TextView)
        val image=(header.findViewById(R.id.seekerProfileUrl) as ImageView)
        Glide.with(applicationContext)
                .load(mFirebaseUser.photoUrl)
                .into(image)
        name.text = mFirebaseUser.displayName
        email.text = mFirebaseUser.email
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

    override fun lunchJobDetail(jobListVO: JobListVO) {
        startActivity(JobDetailActivity.newIntent(applicationContext, jobListVO.jobPostId!!))
    }

    private fun googleAuthenticate() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("941470455059-mouepersmu56dn9mc5b5jne76leonj4v.apps.googleusercontent.com")
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.RC_GOOGLE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            processGoogleSignInResult(result)
        }
    }

    private fun processGoogleSignInResult(signInResult: GoogleSignInResult) {
        if (signInResult.isSuccess) {
            val account = signInResult.signInAccount
            JobListModel.getInstance().authenticateUserWithGoogleAccount(account!!, object : JobListModel.SignInWithGoogleAccountDelegate {
                override fun onSuccessSignIn(signInAccount: GoogleSignInAccount) {
                    startPostJobActivity()
                }

                override fun onFailureSignIn(msg: String) {
                    EmptyError(msg)
                }
            })
        } else {
            Snackbar.make(jobRecycler, "Your Google sign-in failed.", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, AppConstants.RC_GOOGLE_SIGN_IN)
    }

    override fun onChanged(error: Error?) {
        error?.let {
            when (it) {
                is EmptyError -> {
                    emptyLayout.visibility = View.VISIBLE
                    jobRecycler.setEmptyView(emptyLayout)
                }
                is DatabaseError -> UtilGeneral.showNetworkError(rootLayout, applicationContext, error as NetworkError)
            }
        }
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun startPostJobActivity() {
        startActivity(PostJobActivity.newIntent(applicationContext))

    }
}
