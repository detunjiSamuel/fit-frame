package com.example.contexttrigger.dataProducers

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.IBinder



class InternetConnectivityRecording : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork

        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        val isConnected = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (isConnected) {
            val intent = Intent("INTERNET")
            sendBroadcast(intent)
        } else {
            val intent = Intent("NO_INTERNET")
            sendBroadcast(intent)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}