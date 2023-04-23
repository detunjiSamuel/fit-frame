package com.example.contexttrigger.sensorManager

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.contexttrigger.R
import com.example.contexttrigger.notificationManager.NotificationManagerI
import com.example.contexttrigger.triggerManager.TriggerController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

private const val NOTIFICATION_ID = 1001
private const val NOTIFICATION_CHANNEL_ID = "Channel_Id"
private const  val WAIT_PERIOD = 21600000 // 6hrs

class SensorUpdatesHandler : Service() {

    private var sensorHelper = SensorManagerHelper()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        Log.d("dev-log:SensorsUpdatesHandler", "Creating  manager...")

        super.onCreate()

        startForeground(
            NOTIFICATION_ID, sendNotification(
                "Service is running", "Service enabled"
            ).build()
        )

        startValidListeners()
        Log.d("dev-log:SensorsUpdatesHandler", "Finished startValidListeners")

    }

    private fun startValidListeners(){


        for (contextListener in sensorHelper.getActiveDataProducers(this )){

            Log.d("dev-log:SensorsUpdates:StartValidListeners", contextListener.publicName )

           val intent = Intent(this ,  contextListener.instance)
            startService(intent)

            if (contextListener.isPendingIntent) {

                Log.d("dev-log:SensorsUpdates:StartValidListeners", "isPendingIntent" )

                val cal = Calendar.getInstance()

                val pendingVersion = PendingIntent.getService(this,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE)

                val alarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    cal.timeInMillis, WAIT_PERIOD.toLong(),
                    pendingVersion)

            }


        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("dev-log:SensorsUpdatesHandler", "onStartCommand Created")
        super.onStartCommand(intent, flags, startId)

        var bundle: Bundle? = intent?.extras

        var hasUpdates = bundle != null && intent != null

        if (bundle != null && intent != null)
        {
            // PASS DATA TO HANDLERS

            if (intent.hasExtra("CREATED_FOR"))
            {
                // DISPATCH TO ALL TRIGGERS

                var destination: String? = intent.getStringExtra("CREATED_FOR")
                var data : String? =  intent.getStringExtra("DATA")
                if (destination != null && data !=  null) {

                    Log.d("dev-log:SensorsUpdatesHandler", "dispatching to")
                    Log.d("dev-log:SensorsUpdatesHandler", destination)
                    Log.d("dev-log:SensorsUpdatesHandler", data)


                    GlobalScope.launch {

                        TriggerController.handleDataDispatch(
                            this@SensorUpdatesHandler,
                            destination,
                            data

                        )
                    }



                }

             GlobalScope.launch {
                    Log.d("dev-log:SensorsUpdatesHandler", "dispatch done")
                    Log.d("dev-log:SensorsUpdatesHandler", "Notification checking")
                    NotificationManagerI().runRequiredNotifications(this@SensorUpdatesHandler)
                }
            }




        }
        else
        {


            // CHECK FOR TRIGGER NOTIFICATION

            GlobalScope.launch {
                NotificationManagerI().runRequiredNotifications(this@SensorUpdatesHandler)
            }

            // COULD BE USEFUL
            startForeground(
                NOTIFICATION_ID, sendNotification(
                    "Service is running", "Service enabled"
                ).build()
            )


        }

        return START_STICKY

    }



    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(title: String, message: String): android.app.Notification.Builder {
        val notificationChannel =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID,
                NotificationManager.IMPORTANCE_HIGH
            )

        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(notificationChannel)

        return android.app.Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentText(title)
            .setContentTitle(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
    }
}