package com.example.contexttrigger.dataProducers

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.IBinder
import com.example.contexttrigger.broadcastReceivers.InternetConnectivityReceiver


class InternetConnectivityRecording : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val isConnected = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        val intentFilter = IntentFilter("INTERNET")
        registerReceiver(InternetConnectivityReceiver(), intentFilter)
        val data = if (isConnected) "YES" else "NO"
        val intent = Intent("INTERNET").apply {
            putExtra("DATA", data)
        }
        sendBroadcast(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}