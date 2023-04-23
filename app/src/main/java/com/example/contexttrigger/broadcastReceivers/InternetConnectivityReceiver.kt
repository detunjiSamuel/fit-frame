package com.example.contexttrigger.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.contexttrigger.sensorManager.SensorController

class InternetConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("dev-log:InternetConnectivityReceiver", "onReceive")
        val resourceName = "airplane-mode"
        val hasInternet = intent.getStringExtra("DATA")
        val data = "$resourceName|${if (hasInternet == "YES") "YES" else "NO"}"
        val serviceIntent = Intent(context, SensorController::class.java).apply {
            putExtra("IS_REPORTING", "DEVICE_RESOURCE_CHANGE")
            putExtra("DATA", data)
        }
        context.startService(serviceIntent)
    }
}