package com.hanifkf12.finalsubmissionfundamental.ui.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.service.DailyReminderReceiver
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    companion object{
        const val DAILY_REMINDER = "daily_reminder"
    }
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dailyReminderReceiver: DailyReminderReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = getString(R.string.settings)
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        dailyReminderReceiver = DailyReminderReceiver()
        sw_reminder.isChecked = sharedPreferences.getBoolean(DAILY_REMINDER,false)

        sw_reminder.setOnCheckedChangeListener { _, checked ->
            sharedPreferences.edit().apply {
                putBoolean(DAILY_REMINDER, checked)
                apply()
            }
            if(checked){
                dailyReminderReceiver.setAlarmDaily(this)
            }else{
                dailyReminderReceiver.cancelDailyReminder(this)
            }
        }

        settings_change_language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }
}
