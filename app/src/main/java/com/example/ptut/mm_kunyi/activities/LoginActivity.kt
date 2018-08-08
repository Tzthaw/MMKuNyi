package com.example.ptut.mm_kunyi.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity
import com.example.ptut.mm_kunyi.models.JobListModel
import com.example.ptut.mm_kunyi.mvp.presenters.JobListPresenter
import com.example.ptut.mm_kunyi.mvp.views.JobListView
import com.example.ptut.mm_kunyi.utils.AppConstants
import com.example.ptut.mm_kunyi.utils.EmptyError
import com.example.ptut.mm_kunyi.vos.JobListVO
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_main.*

class LoginActivity : BaseActivity(), View.OnClickListener,JobListView {
    override fun tapComment(jobId: String) {

    }

    override fun setLikeCount(jobId: String, likeId: Int) {
    }

    private var mGoogleApiClient: GoogleApiClient? = null

    lateinit var mJobListPresenter:JobListPresenter
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mJobListPresenter=ViewModelProviders.of(this).get(JobListPresenter::class.java)
        mJobListPresenter.initPresenter(this)

        loginGoogle.setOnClickListener(this)
        loginButton.setOnClickListener(this)
        register.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.register->{
                startActivity(RegisterActivity.newIntent(applicationContext))
            }
            R.id.loginButton->{
                loginUser()
            }
            R.id.loginGoogle->{
                if(mFirebaseUser!=null){
                   startJobListActivity()
                }else{
                    signInWithGoogle()
                }
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, AppConstants.RC_GOOGLE_SIGN_IN)
    }

    private fun loginUser(){
        val email = userLogin.text.toString()
        val password = passwordLogin.text.toString()
        if (!email.isEmpty() && !password.isEmpty()) {
           mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    startActivity(JobListActivity.newIntent(applicationContext))
                    finish()
                } else {
                    Snackbar.make(loginLayout, "Error Loading", Snackbar.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Please fill up the Credentials", Toast.LENGTH_SHORT).show()
        }
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
                    startJobListActivity()
                }

                override fun onFailureSignIn(msg: String) {
                    EmptyError(msg)
                }
            })
        } else {
            Snackbar.make(jobRecycler, "Your Google sign-in failed.", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun startJobListActivity(){
        startActivity(RegisterActivity.newIntent(applicationContext))
        finish()
    }

    override fun lunchJobDetail(jobListVO: JobListVO) {

    }
}
