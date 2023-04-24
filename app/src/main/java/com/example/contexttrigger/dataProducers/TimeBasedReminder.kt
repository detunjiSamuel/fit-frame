package com.example.contexttrigger.dataProducers

import android.app.Service
import android.app.PendingIntent
import android.content.Intent
import android.os.IBinder

import android.content.BroadcastReceiver
import android.content.Context

import java.time.LocalDateTime

import android.app.AlarmManager
import android.util.Log
import com.example.contexttrigger.sensorManager.SensorUpdatesHandler

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("dev-log:TimeBasedReminder", "Got Alarm")
        val intent  = Intent (context , SensorUpdatesHandler::class.java)

        intent.putExtra("CREATED_FOR" ,  "ALARM_DATA_PRODUCER" )

        context?.startService(intent)
    }
}

class TimeBasedReminder: Service() {

    private var nextAlarm: LocalDateTime? = null
    private var alarmReceiver: AlarmReceiver? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        Log.d("dev-log:TimeBasedReminder", "starting...")
        alarmReceiver = AlarmReceiver()

        // get current time in milliseconds since the epoch
        val currentTimeMillis = System.currentTimeMillis()

        // First reminder, 60 seconds from startup, just a POC
        setAlarm(currentTimeMillis + 20000)


        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    // Set alarm to trigger at the specified time
    private fun setAlarm(triggerAtMillis: Long) {
        Log.d("dev-log:TimeBasedReminder", "Setting alarm for $triggerAtMillis")
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setExact(AlarmManager.RTC, triggerAtMillis, pendingIntent)
    }

}