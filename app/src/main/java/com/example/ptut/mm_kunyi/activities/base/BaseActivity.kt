package com.example.ptut.mm_kunyi.activities.base

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.ptut.mm_kunyi.models.JobListModel

import com.example.ptut.mm_kunyi.utils.Error
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), Observer<Error> {
    lateinit var mAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        mAuth = JobListModel.getInstance().getAuth()
    }

    override fun onChanged(error: Error?) {

    }
}