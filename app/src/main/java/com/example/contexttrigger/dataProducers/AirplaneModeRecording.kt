package com.example.contexttrigger.dataProducers

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.example.contexttrigger.broadcastReceivers.AirplaneModeReceiver

class AirplaneModeRecording : Service() {

    private lateinit var airplaneModeReceiver: AirplaneModeReceiver

    override fun onCreate() {
        super.onCreate()
        airplaneModeReceiver = AirplaneModeReceiver()
        registerReceiver(airplaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeReceiver)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}