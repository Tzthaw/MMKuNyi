package com.example.ptut.mm_kunyi.fcm

import android.annotation.SuppressLint
import android.util.Log
import com.example.ptut.mm_kunyi.KuNyiApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

@SuppressLint("Registered")
class AppFirebaseInstaceIdService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token
        Log.d(KuNyiApp.TAG, "FCM Token : " + token!!)
    }
}
