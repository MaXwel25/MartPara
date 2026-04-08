package com.example.martpara

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class Utils {
    companion object {
        fun generateID(): Int {
            return (Math.random() * 1000000).roundToInt()
        }

        fun checkZero(num: Int): String {
            return if (num < 10) "0$num" else num.toString()
        }

        fun getCurrentDate(): String {
            return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        }

        @SuppressLint("SimpleDateFormat")
        fun dateTimeMillisec(date: String, time: String): Long {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
            return dateFormat.parse("$date $time")?.time ?: 0L
        }

        @SuppressLint("SimpleDateFormat")
        fun millisecondsToDate(milliseconds: Long): String {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            return dateFormat.format(Date(milliseconds))
        }

        @SuppressLint("SimpleDateFormat")
        fun millisecondsToTime(milliseconds: Long): String {
            val dateFormat = SimpleDateFormat("HH:mm")
            return dateFormat.format(Date(milliseconds))
        }

        fun getPendingIntent(context: Context, id: Int, text: String): PendingIntent {
            val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
                putExtra("text", text)
                putExtra("id", id)
            }
            return PendingIntent.getBroadcast(
                context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}