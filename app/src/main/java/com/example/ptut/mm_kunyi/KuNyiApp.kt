package com.example.ptut.mm_kunyi

import android.app.Application
import com.example.ptut.mm_kunyi.models.JobListModel
import com.google.firebase.FirebaseApp

class KuNyiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        JobListModel.initJobListModel(this)
    }

}