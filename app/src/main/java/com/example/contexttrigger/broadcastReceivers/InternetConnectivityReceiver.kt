package com.example.contexttrigger.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class InternetConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val action = intent.action
        if (action == "INTERNET") {
            // Do something when internet is connected
        } else if (action == "NO_INTERNET") {
            // Do something when internet is disconnected
        }
    }
}