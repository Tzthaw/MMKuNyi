package com.example.ptut.mm_kunyi.models

import android.content.Context
import com.example.ptut.mm_kunyi.models.base.BaseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class LoginUserModel private constructor(context: Context) : BaseModel(context) {
    companion object {
        private lateinit var mDatabaseReference: DatabaseReference
        private lateinit var mJobListDR: DatabaseReference
        private lateinit var mFirebaseAuth: FirebaseAuth
        private var mFirebaseUser: FirebaseUser? = null
        private var INSTANCE: LoginUserModel? = null

        fun getInstance(): LoginUserModel {
            if (INSTANCE == null) {
                throw RuntimeException("HealthCareInfoModel is being invoked before initializing.")
            }
            val i = INSTANCE
            return i!!
        }

        fun initJobListModel(context: Context) {
            INSTANCE = LoginUserModel(context)
        }

    }

    init {

    }

}