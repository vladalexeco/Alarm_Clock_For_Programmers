package ru.vladalexeco.alarmclockforprogrammers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.vladalexeco.alarmclockforprogrammers.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.timePicker.setIs24HourView(true)
        bind.timePicker.hour = 12
        bind.timePicker.minute = 0

        bind.turnOnButton.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.set(Calendar.MINUTE, bind.timePicker.minute)
            calendar.set(Calendar.HOUR_OF_DAY, bind.timePicker.hour)

            val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            var startUpTime: Long = calendar.timeInMillis

            if (System.currentTimeMillis() > startUpTime) {
                startUpTime += AlarmManager.INTERVAL_DAY
                val alarmClockInfo: AlarmManager.AlarmClockInfo = AlarmManager.AlarmClockInfo(startUpTime, getAlarmInfoPendingIntent())
                alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent())
            } else {
                val alarmClockInfo: AlarmManager.AlarmClockInfo = AlarmManager.AlarmClockInfo(calendar.timeInMillis, getAlarmInfoPendingIntent())
                alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent())
            }

            Toast.makeText(this, "Будильник сработает в ${sdf.format(calendar.time)}", Toast.LENGTH_LONG).show()
        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getAlarmInfoPendingIntent(): PendingIntent {
        val alarmInfoIntent = Intent(this, MainActivity::class.java)
        alarmInfoIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getAlarmActionPendingIntent(): PendingIntent {
        val intent = Intent(this, TaskScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE) 
    }

    fun check() {}
}