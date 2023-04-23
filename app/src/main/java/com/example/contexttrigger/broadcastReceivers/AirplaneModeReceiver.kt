package com.example.contexttrigger.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.contexttrigger.sensorManager.SensorController
import com.example.contexttrigger.dataProducers.LOCATION_RECORDING_PUBLIC_NAME



class AirplaneModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                val isAirplaneModeOn = intent.getBooleanExtra("state", false)

                Log.d("dev-log::AirplaneModeReceiver", "onReceive")

                val resourceName = "airplane-mode"
                val data = "$resourceName|${if (isAirplaneModeOn) "NO" else "YES"}"
                val serviceIntent = Intent(context, SensorController::class.java).apply {
                    putExtra("IS_REPORTING", "DEVICE_RESOURCE_CHANGE")
                    putExtra("DATA", data)
                }
                context.startService(serviceIntent)
            }
        }




}