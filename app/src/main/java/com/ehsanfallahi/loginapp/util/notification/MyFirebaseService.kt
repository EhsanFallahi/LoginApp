package com.ehsanfallahi.loginapp.util.notification

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.ehsanfallahi.loginapp.util.Constant.Companion.MY_TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService:FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        Log.d(MY_TAG,"fcm token is:$token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(MY_TAG, "From: ${remoteMessage?.from}")

        remoteMessage?.data?.let {
            Log.d(MY_TAG, "Message data payload: " + remoteMessage.data)
        }

        remoteMessage?.notification?.let {
            Log.d(MY_TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body!!)
        }
    }
    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(applicationContext,
            NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }
}