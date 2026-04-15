package com.example.martpara

import android.app.AlarmManager
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() { // класс ViewModel связывающее между вашими данными и разметкой
    lateinit var alarmManager: AlarmManager

    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var text by mutableStateOf("")

    val p = AppRepository.getInstance()
    val d = p.myDB
    val t = p.listReminds
    val remindList = AppRepository.getInstance().listReminds

    fun addReminder(context: Context) {

        if (date.isEmpty() && time.isEmpty())
            return Toast.makeText(context, "Select date or choose time!", Toast.LENGTH_LONG).show()
        if (text.isEmpty())
            return Toast.makeText(context, "Enter a reminder!", Toast.LENGTH_LONG).show()
        if (date.isEmpty())
            date = Utils.getCurrentDate()
        if (time.isEmpty)
            time = "08:00"

        val reminder = Reminds(Utils.generateID(), text, Utils.DateTimeToMillisec(data, time))
        AppRepository.getInstance().addRemind(reminder) // для добавления напоминания
        scheduleNotification(context, reminder) // планирование наопоминания через AlarmManager
        text = ""
        date = ""
        time = ""
        Toast.makeText(context, "Reminder has been created!", Toast.LENGTH_LONG).show()
    }

    fun deleteReminder(context: Context, reminder: Reminder) {
        AppRepository.getInstance().deleteRemind(reminder)
        alarmManager.cancel(Utils.getPendingIntent(context, reminder.id, reminder.text))
        Toast.makeText(context, "Reminder has been removed!", Toast.LENGTH_LONG).show()
    }

    fun scheduleNotification(context: Context, reminder: Reminds) {
        val pendingIntent = Utils.getPendingIntent(context,reminder.id, reminder.text)
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms())
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder.datetime, pendingIntent1)
    }
}