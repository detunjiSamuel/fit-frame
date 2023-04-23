package com.example.contexttrigger.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.contexttrigger.components.sensorManager.SensorController
import com.example.contexttrigger.dataProducers.LOCATION_RECORDING_PUBLIC_NAME

class AirplaneModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeOn = intent.getBooleanExtra("state", false)

            val resourceName = "airplane-mode"

            if (isAirplaneModeOn) {
                // Airplane mode is on


                val intent = Intent(context , SensorController::class.java)

                intent.putExtra("IS_REPORTING" , "DEVICE_RESOURCE_CHANGE" )

                intent.putExtra("DATA" , "$resourceName|NO")

                context.startService(intent)


            } else {
                // Airplane mode is off
                val intent = Intent(context , SensorController::class.java)

                intent.putExtra("IS_REPORTING" , "DEVICE_RESOURCE_CHANGE" )

                intent.putExtra("DATA" , "$resourceName|YES")

                context.startService(intent)
            }
        }
    }


}