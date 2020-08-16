package com.hanifkf12.finalsubmissionfundamental.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.hanifkf12.finalsubmissionfundamental.MainActivity
import com.hanifkf12.finalsubmissionfundamental.R
import java.util.*

class DailyReminderReceiver : BroadcastReceiver() {
    companion object {
        private const val EXTRA_DAILY_REMINDER = "extra_daily_reminder"
        private const val TYPE = "type"
        private const val ID_REPEATING = 101
    }


    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(TYPE)
        Log.d("COBAAAA", type)
        Toast.makeText(context, type,Toast.LENGTH_SHORT).show()
        type?.let {
            if (type == EXTRA_DAILY_REMINDER) {
                sendDailyReminder(context)
            }
        }

    }

    private fun intent(context: Context,type: String): Intent? {
        val intent = Intent(
            context,
            DailyReminderReceiver::class.java
        )
        intent.putExtra(TYPE, type)
        return intent
    }

    fun setAlarmDaily(context: Context) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_REPEATING,
            intent(context, EXTRA_DAILY_REMINDER),
            0
        )
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 9
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, "Daily Reminder set up", Toast.LENGTH_SHORT).show()
    }

    private fun cancelReminder(context: Context) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(
            context,
            DailyReminderReceiver::class.java
        )
        val requestCode = ID_REPEATING
        val pendingIntent =
            PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Daily Reminder cancel", Toast.LENGTH_SHORT).show()
    }

    fun cancelDailyReminder(context: Context) {
        cancelReminder(
            context
        )
    }

    private fun sendDailyReminder(context: Context) {
        notificationDaily(
            context, context.getString(R.string.app_name),
            context.getString(R.string.app_name) + " " + context.getString(R.string.daily_message)
        )
    }


    private fun notificationDaily(context: Context, title: String, desc: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val channelId = "notification_daily"
        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_github)
                .setContentTitle(title)
                .setContentText(desc)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel github app daily reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}
