package com.example.contexttrigger.dataProducers

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import com.example.contexttrigger.components.SensorUpdatesHandler

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.contexttrigger.helpers.ApiRateLimiter
import org.json.JSONObject

const val WEATHER_RECORDING_PUBLIC_NAME = "WEATHER_ACTIVITY_RECORDING"

const val API_KEY = ""

/*
* USAGE :
*   when data is between 800 to 806 :  GOOD WEATHER
*   else:
*       BAD WEATHER
* */

class WeatherRecording : LocationRecording() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("dev-log:WeatherRecording", "weather recording started")

        super.onStartCommand(intent, flags, startId)

        return START_STICKY
    }


    override fun onLocationChanged(location: Location) {

        Log.d("dev-log:WeatherRecording", "newLocation:newWeather")

        val apiRateLimiter = ApiRateLimiter(this)


        if (!apiRateLimiter.canMakeApiRequest()) {
            Log.d("dev-log:WeatherRecording", "api has limit for call sorry")

            val intent = Intent(this, SensorUpdatesHandler::class.java)

            startService(intent)

        } else {

            apiRateLimiter.updateLastRequestTime()
            val queue = Volley.newRequestQueue(this)

            val url = buildUrl(location)

            var req = StringRequest(
                Request.Method.GET,
                url,
                { response ->

                    val obj = JSONObject(response)
                    val data = obj.getJSONArray("data")
                        .getJSONObject(0)
                        .getJSONObject("weather")

                    Log.d("dev-log:WeatherRecording", "weather request completed")
                    Log.d("dev-log:WeatherRecording", data.toString())

                    val intent = Intent(this, SensorUpdatesHandler::class.java)


                    intent.putExtra("CREATED_FOR", WEATHER_RECORDING_PUBLIC_NAME)
                    intent.putExtra("DATA", data.getInt("code").toString())

                    startService(intent)

                },
                {

                    Log.d("dev-log:WeatherRecording", "Error...")
                    //TODO decide if to fire notification
                }

            )

            queue.add(req)
        }


    }


    private fun buildUrl(location: Location): String {
        return "https://api.weatherbit.io/v2.0/current?lat=" +
                location.latitude + "&lon=" +
                location.longitude + "&key=" +
                API_KEY
    }
}