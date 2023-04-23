package com.example.contexttrigger.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.contexttrigger.components.sensorManager.SensorController

class InternetConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {


        Log.d("dev-log:dev-log:InternetConnectivityReceiver", "onReceive")



        val resourceName = "airplane-mode"


        val hasInternet = intent.getStringExtra("DATA")

        if (hasInternet == "YES") {


            val intent = Intent(context , SensorController::class.java)

            intent.putExtra("IS_REPORTING" , "DEVICE_RESOURCE_CHANGE" )

            intent.putExtra("DATA" , "$resourceName|YES")

            context.startService(intent)

        } else  {

            val intent = Intent(context , SensorController::class.java)

            intent.putExtra("IS_REPORTING" , "DEVICE_RESOURCE_CHANGE" )

            intent.putExtra("DATA" , "$resourceName|NO")

            context.startService(intent)
        }
    }
}