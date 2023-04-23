package com.example.contexttrigger.triggers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.contexttrigger.SHARED_PREFERENCES_NAME
import com.example.contexttrigger.triggerManager.Trigger
import com.example.contexttrigger.dataProducers.WEATHER_RECORDING_PUBLIC_NAME
import com.example.contexttrigger.db.steps.GetSteps
import com.example.contexttrigger.helpers.TimeHelper


private const val NOTIFICATION_TITLE = "Weather Trigger"
private const val GOOD_WEATHER_MESSAGE = "Good weather do you think ?" +
        ", have a walk!"
private const val BAD_WEATHER_MESSAGE = "The weather not so great but" +
        ", maybe do some exercise indoors!"


private const val DEFAULT = "EMPTY"
class Weather : Trigger {

    private var dataProducerNeeded =  arrayOf(WEATHER_RECORDING_PUBLIC_NAME)


    override fun getDataProducerNeeded(): Array<String> {
        return dataProducerNeeded

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun shouldRunNotification(context: Context): Boolean {

        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val stored = sharedPref.getString("weather-trigger-info", DEFAULT)

        if (stored != DEFAULT && stored != null )
        {
            val parts = stored.split(",")

            val timeCreated = parts[1]

            val timeHelp = TimeHelper()

            var hourDifference = timeHelp.calculateHoursDifference(timeCreated)

            val isCloseToNow =  hourDifference < 3 // less than 3hrs away


            if (isCloseToNow)
            {

                val todaySteps = GetSteps()(context , timeHelp.currentDate()) ?: return true

                val isTooFar = timeHelp.
                calculateHoursDifference(todaySteps.updatedAt.toString()) > 3 // more than 3hrs

                if (isTooFar) return true

            }

        }

        return false
    }

    override suspend fun handle(context: Context, createdBy: String, data: String) {

        // store in shared Preference

        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        var toStore = data + ',' + System.currentTimeMillis()

        with(sharedPref.edit()) {
            putString("weather-trigger-info", toStore)
            apply()
        }

    }

    override fun getNotificationTitle(): String {
        return NOTIFICATION_TITLE
    }

    override suspend fun getNotificationMessage(context: Context): String {

        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val stored = sharedPref.getString("weather-trigger-info", DEFAULT)

        if (stored != DEFAULT && stored != null ) {
            val parts = stored.split(",")

            val weatherCode = parts[0].toInt()

            sharedPref.edit().remove("weather-trigger-info").apply()

            if (weatherCode >= 800)
                return GOOD_WEATHER_MESSAGE
            return BAD_WEATHER_MESSAGE

        }
        return GOOD_WEATHER_MESSAGE

    }



}