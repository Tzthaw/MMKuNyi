package com.example.ptut.mm_kunyi.models

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.ptut.mm_kunyi.models.base.BaseModel
import com.example.ptut.mm_kunyi.utils.AppConstants
import com.example.ptut.mm_kunyi.utils.EmptyError
import com.example.ptut.mm_kunyi.utils.Error
import com.example.ptut.mm_kunyi.vos.ApplicantVO
import com.example.ptut.mm_kunyi.vos.CommentVO
import com.example.ptut.mm_kunyi.vos.JobListVO
import com.example.ptut.mm_kunyi.vos.LikeVO
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*

class JobListModel private constructor(context: Context) : BaseModel(context) {
    companion object {
        private lateinit var mDatabaseReference: DatabaseReference
        private lateinit var mFirebaseAuth: FirebaseAuth
        private var mFirebaseUser: FirebaseUser? = null
        private lateinit var mJobInfoDR: DatabaseReference
        private var INSTANCE: JobListModel? = null
        fun getInstance(): JobListModel {
            if (INSTANCE == null) {
                throw RuntimeException("HealthCareInfoModel is being invoked before initializing.")
            }
            val i = INSTANCE
            return i!!
        }

        fun initJobListModel(context: Context) {
            INSTANCE = JobListModel(context)
        }
    }

    fun getJobListData(mErrorLD: MutableLiveData<Error>) {
        mJobInfoDR.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val jobListArray = ArrayList<JobListVO>()
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val jobListItem = snapshot.getValue<JobListVO>(JobListVO::class.java)
                        jobListItem!!.jobId = snapshot.ref.key!!
                        if (jobListItem.like == null) {
                            jobListItem.like = ArrayList()
                        }
                        if (jobListItem.comment == null) {
                            jobListItem.comment = ArrayList()
                        }
                        if (jobListItem.applicant == null) {
                            jobListItem.applicant = ArrayList()
                        }
                        if (jobListItem.interested == null) {
                            jobListItem.interested = ArrayList()
                        }
                        if (jobListItem.jobTags == null) {
                            jobListItem.jobTags = ArrayList()
                        }
                        if (jobListItem.viewed == null) {
                            jobListItem.viewed = ArrayList()
                        }
                        if (jobListItem.viewed == null) {
                            jobListItem.viewed = ArrayList()
                        }
                        if (jobListItem.relevant == null) {
                            jobListItem.relevant = ArrayList()
                        }
                        jobListArray.add(jobListItem)
                    }
                    savePersistence(jobListArray)
                } else {
                    mErrorLD.value = EmptyError("Null Data")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                mErrorLD.value = com.example.ptut.mm_kunyi.utils.DatabaseError(databaseError.message)
            }
        })
    }

    private fun savePersistence(jobList: List<JobListVO>) {
        val ids: LongArray = mTheDB.jobListDao().insertAll(jobList)
        Log.e("JobList", "${ids.size}")
    }

    fun getJobList(): LiveData<List<JobListVO>> {
        return mTheDB.jobListDao().getJobList()
    }

    fun getJobById(jobId: Int): LiveData<JobListVO> {
        return mTheDB.jobListDao().getJobById(jobId)
    }

    fun getJobByIdComment(jobId: String): LiveData<JobListVO> {
        Log.e("List", mTheDB.jobListDao().getJobByIdComment(jobId).value.toString())
        return mTheDB.jobListDao().getJobByIdComment(jobId)
    }

    fun authenticateUserWithGoogleAccount(signInAccount: GoogleSignInAccount, delegate: SignInWithGoogleAccountDelegate) {
        val credential = GoogleAuthProvider.getCredential(signInAccount.idToken, null)
        mFirebaseAuth.signInWithCredential(credential)
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

    fun getAuth(): FirebaseAuth {
        return mFirebaseAuth
    }

    fun applyJob(jobId: String, applicantId: String, callback: SetValueCallBack) {
        val applicant: ApplicantVO = ApplicantVO.initApplicant(mFirebaseUser!!.displayName,
                mFirebaseUser!!.photoUrl.toString())
        mJobInfoDR.child(jobId).child("applicant")
                .child(applicantId).setValue(applicant)
        callback.onApplySuccess("Success Apply Your Information!!")
    }

    fun addLike(jobId: String, likeId: String) {
        if (mFirebaseUser != null) {
            val like: LikeVO = LikeVO.initLike(mFirebaseUser!!.uid)
            mJobInfoDR.child(jobId).child("like")
                    .child(likeId).setValue(like)
        }

    }

    fun addUnLike(jobId: String, likeId: String) {
        var like: LikeVO = LikeVO.initLike(mFirebaseUser!!.uid)
        mJobInfoDR.child(jobId).child("like")
                .child(likeId).removeValue()
    }

    fun addComment(jobId: String, commentId: String, details: String) {
        val comment = CommentVO.initComment(mFirebaseUser!!.uid, mFirebaseUser!!.displayName!!,
                mFirebaseUser!!.photoUrl!!.toString(), details)
        mJobInfoDR.child(jobId).child("comment")
                .child(commentId).setValue(comment)
    }

    fun addPost(jobId: String, jobListVO: JobListVO, callback: SetValueCallBack) {
        mJobInfoDR.child(jobId).setValue(jobListVO)
        callback.onApplySuccess("Success Post Publish!!")

    }

    init {
        mDatabaseReference = FirebaseDatabase.getInstance().reference
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser = mFirebaseAuth.currentUser
        mJobInfoDR = mDatabaseReference.child(AppConstants.KUNYI_INFO_DR)
    }

    interface SetValueCallBack {
        fun onApplySuccess(msg: String)
    }

    interface SignInWithGoogleAccountDelegate {
        fun onSuccessSignIn(signInAccount: GoogleSignInAccount)
        fun onFailureSignIn(msg: String)
    }


}