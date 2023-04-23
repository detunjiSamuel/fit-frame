package com.example.contexttrigger.dataProducers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.contexttrigger.sensorManager.SensorUpdatesHandler
import com.example.contexttrigger.helpers.LocationHelper


/**
 *  USAGE:
 *      remember to use LocationHelper().StringToLocation(string)
 *      when reading the data value
 *
 * */


// 1 min (will change to 1 hour). The Minimum Time to get location update
private const val LOCATION_REFRESH_TIME = 7200000
// 500 meters. The Minimum Distance to be changed to get location update
private const val LOCATION_REFRESH_DISTANCE = 1000

const val LOCATION_RECORDING_PUBLIC_NAME = "LOCATION_ACTIVITY_RECORDING"


open class LocationRecording : Service() , LocationListener {



    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }


    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("dev-log:locationRecording", "starting location recording...")
        super.onStartCommand(intent, flags, startId)
        if (hasLocationPermissions()) {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_REFRESH_TIME.toLong(),
                LOCATION_REFRESH_DISTANCE.toFloat(),
                this
            )
        } else {
            Log.d("dev-log:locationRecording", "cannot get location info...")
            val intent = Intent(this, SensorUpdatesHandler::class.java).apply {
                putExtra("CREATED_FOR", LOCATION_RECORDING_PUBLIC_NAME)
                putExtra("DATA", -1)
            }
            startService(intent)
            onDestroy()
        }
        return START_STICKY
    }

    private fun hasLocationPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    override fun onLocationChanged(location: Location) {

        Log.d("dev-log:locationRecording:onLocationChange", "New location")

        Log.d("dev-log:locationRecording:onLocationChange",
            "New location: " + location.latitude + ", " + location.longitude)

        val intent  = Intent (this , SensorUpdatesHandler::class.java)

        intent.putExtra("CREATED_FOR" , LOCATION_RECORDING_PUBLIC_NAME )

        intent.putExtra("DATA", LocationHelper().locationToString(location))

        startService(intent)

    }
}