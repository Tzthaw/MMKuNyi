package com.example.ptut.mm_kunyi.fcm

import android.annotation.SuppressLint
import android.util.Log
import com.example.ptut.mm_kunyi.KuNyiApp
import com.example.ptut.mm_kunyi.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("Registered")
class AppFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // Handle data payload of FCM messages.
        Log.d(KuNyiApp.TAG, "FCM Message : " + remoteMessage!!.messageId!!)
        Log.d(KuNyiApp.TAG, "FCM Notification Message: " + remoteMessage.notification!!)
        Log.d(KuNyiApp.TAG, "FCM Data Message: " + remoteMessage.data)
        val remoteMsgData = remoteMessage.data
        val message = remoteMsgData[NotificationUtils.KEY_MESSAGE]
        NotificationUtils.getInstance().notifyCustomMsg(applicationContext, message!!)
    }
}