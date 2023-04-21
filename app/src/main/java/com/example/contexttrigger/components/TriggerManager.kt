package com.example.contexttrigger.components

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startForegroundService
import com.example.contexttrigger.triggerSamples.HalfWayPointSample

import com.tbruyelle.rxpermissions3.RxPermissions



private const val NOTIFICATION_CHANNEL_ID_RUNNING = "Channel_Id"
private const val NOTIFICATION_CHANNEL_ID_Event = "REGULAR-EVENT"


private val REQUIRED_PERMISSIONS = arrayOf(
    Manifest.permission.ACTIVITY_RECOGNITION,
//        Manifest.permission.ACCESS_FINE_LOCATION,
//        Manifest.permission.READ_CALENDAR

    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION

)



class TriggerManager {
    // Main Interface of for user to access/import and use it
    private val triggers = mutableListOf<Trigger>()



    fun registerTrigger(trigger: Trigger) {
        TriggerStore.registerTrigger(trigger)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun invoke(context : Context) {
        // this will ask for permissions based on triggers registered

        // create notification channels
        createNotificationChannels(context)

        // require necessary permissions
        requirePermissions(context as AppCompatActivity)

        // register All needed sensors listeners

        // start the sensorUpdateHandler
        Log.d("dev-log:TriggerManager:sensorUpdated", "started")

        val intent = Intent(context , SensorUpdatesHandler::class.java )

        ContextCompat.startForegroundService(context.applicationContext, intent)

    }

    fun cleanup(){
        // tasks that have to stop
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannels(context: Context) {


        Log.d("dev-log:TriggerManager:crateNoteChannel", "started")


        // have two now
        val notificationChannelAppRunning =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID_RUNNING,
                NOTIFICATION_CHANNEL_ID_RUNNING,
                NotificationManager.IMPORTANCE_HIGH
            )
        // regular event from trigger
        val notificationChannelEvent =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID_Event,
                NOTIFICATION_CHANNEL_ID_Event,
                NotificationManager.IMPORTANCE_DEFAULT
            )

        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(notificationChannelAppRunning)

        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(notificationChannelEvent)

        Log.d("dev-log:TriggerManager:crateNoteChannel", "ended")


    }

    private fun requirePermissions(activity : AppCompatActivity) {

        var rxPermissions = RxPermissions(activity)

        rxPermissions.requestEachCombined(
            Manifest.permission.ACTIVITY_RECOGNITION,
//            Manifest.permission.READ_CALENDAR,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).subscribe { permission ->
                if (permission.granted) {
                    Log.d("dev-log:TriggerManager:requirePermissions", "permission.granted")
//                    val weather = Intent(this, WeatherLocationData::class.java)
//                    startService(weather)
                }
                if (!permission.granted) {
                    Log.d("dev-log:TriggerManager:requirePermissions", "permission.not-granted")

                    ActivityCompat.requestPermissions(
                        activity,
                        REQUIRED_PERMISSIONS,
                        100
                    )
                }
            }

    }

}