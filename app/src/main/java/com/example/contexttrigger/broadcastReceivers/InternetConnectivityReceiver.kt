package com.example.contexttrigger.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class InternetConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {


        val hasInternet = intent.getStringExtra("DATA")
        if (hasInternet == "YES") {
            // Do something when internet is connected
        } else  {
            // Do something when internet is disconnected
        }
    }
}