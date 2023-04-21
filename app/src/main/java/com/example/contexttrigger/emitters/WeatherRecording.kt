package com.example.contexttrigger.emitters

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

const val WEATHER_RECORDING_PUBLIC_NAME = "WEATHER_ACTIVITY_RECORDING"



class WeatherRecording : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("dev-log:WeatherRecording", "weather recording started")


        return START_STICKY
    }
}