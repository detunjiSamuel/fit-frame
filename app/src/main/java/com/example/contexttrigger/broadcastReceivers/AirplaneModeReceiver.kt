package com.example.contexttrigger.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AirplaneModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeOn = intent.getBooleanExtra("state", false)
            if (isAirplaneModeOn) {
                // Airplane mode is on
                val intent = Intent("com.example.contexttrigger.AIRPLANE_MODE_ON")
                context.sendBroadcast(intent)
            } else {
                // Airplane mode is off
                val intent = Intent("com.example.contexttrigger.AIRPLANE_MODE_OFF")
                context.sendBroadcast(intent)
            }
        }
    }
}