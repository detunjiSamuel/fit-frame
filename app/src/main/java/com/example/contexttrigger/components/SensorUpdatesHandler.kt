package com.example.contexttrigger.components

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.contexttrigger.R
import com.example.contexttrigger.listeners.contextListenersList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val NOTIFICATION_ID = 1001
private const val NOTIFICATION_CHANNEL_ID = "Channel_Id"

class SensorUpdatesHandler : Service() {


    private lateinit var triggerManager: TriggerManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        Log.d("requireSensorsUpdates", "Creating  manager...")

        super.onCreate()

        startForeground(
            NOTIFICATION_ID, sendNotification(
                "Service is running", "Service enabled"
            ).build()
        )

        startValidListeners()
        Log.d("requireSensorsUpdates", "Finished startValidListeners")

    }

    private fun startValidListeners(){

        // Trigger should have active Listeners

        for (contextListener in contextListenersList){
            //TODO enforce check to see if it is needed

            Log.d("requireStartValidListeners", contextListener.publicName )

            if (contextListener.isPendingIntent) {
                // pending Intent has unique logic i.e alarm kinds
                // TODO add
                // This does not have standard process
                // will house logic in different file

            }else{
                val intent = Intent(this ,  contextListener.instance)
                startService(intent)
            }


        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("requireSensorsUpdates", "onStartCommand Created")
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

                    Log.d("requireSensorsUpdates", "dispatching to")
                    Log.d("requireSensorsUpdates", destination)
                    Log.d("requireSensorsUpdates", data)


                    TriggerStore.handleDataDispatch(
                        destination,
                        data

                    )
                }

                GlobalScope.launch {
                    Log.d("requireSensorsUpdates", "dispatch done")
                    Log.d("requireSensorsUpdates", "Notification checking")
                    TriggerStore.runNotifications(this@SensorUpdatesHandler)
                }
            }




        }
        else
        {


            // CHECK FOR TRIGGER NOTIFICATION

            /*
            * GET ACTIVE TRIGGERS
            *   RUN SHOULDSHOWNOTIFICATION
            *       PASS CONTROL TO NOTIFICATION HANDLER
            * */

            GlobalScope.launch {
                TriggerStore.runNotifications(this@SensorUpdatesHandler)
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