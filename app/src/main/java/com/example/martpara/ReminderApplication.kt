package com.example.martpara

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import android.provider.Settings

class ReminderApplication: Application() {
    companion object{
        const val channelName="myReminder"
        const val channelDescription="Channel for my Reminder"
        const val channelID="myReminderChannelID"

        private var instance: ReminderApplication? = null
        val context
            get() = applicationContext()

        private fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelID, channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
                .apply {
                    description=channelDescription
                    setSound(
                        Settings.System.DEFAULT_NOTIFICATION_URI,
                        AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build())
                }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}