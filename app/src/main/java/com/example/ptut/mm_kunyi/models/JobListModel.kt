package com.example.ptut.mm_kunyi.models

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.ptut.mm_kunyi.models.base.BaseModel
import com.example.ptut.mm_kunyi.utils.EmptyError
import com.example.ptut.mm_kunyi.utils.Error
import com.example.ptut.mm_kunyi.vos.JobListVO
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import java.util.*

class JobListModel private constructor(context: Context):BaseModel(context){
    companion object {
        private lateinit var mJobListDR: DatabaseReference
        private var mFirebaseAuth: FirebaseAuth?=null
        private var mFirebaseUser: FirebaseUser?=null
        private var INSTANCE:JobListModel?=null

        fun getInstance(): JobListModel {
            if (INSTANCE == null) {
                throw RuntimeException("HealthCareInfoModel is being invoked before initializing.")
            }
            val i= INSTANCE
            return i!!
        }
        fun initJobListModel(context: Context) {
            INSTANCE = JobListModel(context)
        }

    }
    fun getJobListData(mErrorLD:MutableLiveData<Error>){
        mJobListDR.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val jobListArray = ArrayList<JobListVO>()
                if (dataSnapshot != null) {
                    for (snapshot in dataSnapshot.children) {
                        val jobListItem = snapshot.getValue<JobListVO>(JobListVO::class.java)
                        jobListArray.add(jobListItem!!)
                    }
                    savePersistence(jobListArray)
                }else{
                    mErrorLD.value=EmptyError("Null Data")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                mErrorLD.value=com.example.ptut.mm_kunyi.utils.DatabaseError(databaseError.message)
            }
        })
    }
    private fun savePersistence(jobList:List<JobListVO>){
        val ids:LongArray=mTheDB.jobListDao().insertAll(jobList)
        Log.e("JobList","$ids")
    }

    fun getJobList():LiveData<List<JobListVO>>{
        return mTheDB.jobListDao().getJobList()
    }

    fun getJobById(jobId:Int):LiveData<JobListVO>{
        return mTheDB.jobListDao().getJobById(jobId)
    }

    fun authenticateUserWithGoogleAccount(signInAccount: GoogleSignInAccount, delegate: SignInWithGoogleAccountDelegate) {
        val credential = GoogleAuthProvider.getCredential(signInAccount.idToken, null)
        mFirebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        delegate.onFailureSignIn(task.exception!!.message!!)
                    } else {
                        delegate.onSuccessSignIn(signInAccount)
                    }
                }
                .addOnFailureListener { e ->
                    delegate.onFailureSignIn(e.message!!)
                }
    }

    interface SignInWithGoogleAccountDelegate {
        fun onSuccessSignIn(signInAccount: GoogleSignInAccount)
        fun onFailureSignIn(msg: String)
    }

    fun isUserSignIn(): Boolean {
        return mFirebaseUser != null
    }

    fun getUserInfo():FirebaseUser{
        return mFirebaseUser!!
    }

    init {
        mJobListDR = FirebaseDatabase.getInstance().reference
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser= mFirebaseAuth!!.currentUser
    }




}