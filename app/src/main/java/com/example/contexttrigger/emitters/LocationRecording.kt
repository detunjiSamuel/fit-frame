package com.example.contexttrigger.emitters

import android.Manifest
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
import com.example.contexttrigger.components.SensorUpdatesHandler


/** P.S
 * The onLocationChanged method is not standing alone as an overridden method
 * because it is already an overridden method of the LocationListener interface.
 * The LocationRecording class implements this interface by adding LocationListener
 * to its declaration, which means it is required to implement all methods of the
 * LocationListener interface. Therefore, the onLocationChanged method is implemented
 * as part of the LocationListener interface implementation in the LocationRecording class
 *
 * */


// 1 min (will change to 1 hour). The Minimum Time to get location update
private const val LOCATION_REFRESH_TIME = 7200000
// 500 meters. The Minimum Distance to be changed to get location update
private const val LOCATION_REFRESH_DISTANCE = 1000

const val LOCATION_RECORDING_PUBLIC_NAME = "LOCATION_ACTIVITY_RECORDING"


class LocationRecording : Service() , LocationListener {



    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("dev-log:locationRecording", "starting location recording .. ")

        super.onStartCommand(intent, flags, startId)

        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // EDITOR FORCED THIS CONDITION

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Log.d("dev-log:locationRecording", "cannot get location info .. ")

            val intent  = Intent (this , SensorUpdatesHandler::class.java)

            intent.putExtra("CREATED_FOR" , LOCATION_RECORDING_PUBLIC_NAME )

            intent.putExtra("DATA" , -1 )

            startService(intent)

            onDestroy()

        }

        else
        {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_REFRESH_TIME.toLong(),
                LOCATION_REFRESH_DISTANCE.toFloat(),
                this
            )
        }

        return START_STICKY
    }

    override fun onLocationChanged(location: Location) {

        Log.d("dev-log:locationRecording:onLocationChange", "New location")

        val intent  = Intent (this , SensorUpdatesHandler::class.java)

        intent.putExtra("CREATED_FOR" , LOCATION_RECORDING_PUBLIC_NAME )

        intent.putExtra("DATA", location)

        startService(intent)

    }
}