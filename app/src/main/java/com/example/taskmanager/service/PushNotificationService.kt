package com.example.taskmanager.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.taskmanager.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {

        val chanel = NotificationChannel(
            CHANNEL_ID,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        )

        getSystemService(NotificationManager::class.java).createNotificationChannel(chanel)
        Log.e("ololo", "onMessageReceived: ${message.notification?.title}")
        val notification = Notification.Builder(this, CHANNEL_ID)
        notification.apply {
            setContentTitle(message.notification?.title)
            setContentText(message.notification?.body)
            setSmallIcon(R.drawable.ic_google)
            setAutoCancel(true)
        }
        NotificationManagerCompat.from(this).notify(1, notification.build())
    }

    companion object {
        const val CHANNEL_ID = "channel.taskApp"
    }
}