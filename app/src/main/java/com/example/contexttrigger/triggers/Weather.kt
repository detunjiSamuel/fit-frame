package com.example.contexttrigger.triggers

import android.content.Context
import com.example.contexttrigger.components.trigger.Trigger
import com.example.contexttrigger.dataProducers.LOCATION_RECORDING_PUBLIC_NAME
import com.example.contexttrigger.dataProducers.WEATHER_RECORDING_PUBLIC_NAME



private const val NOTIFICATION_TITLE = "Weather Trigger"
private const val GOOD_WEATHER_MESSAGE = "Good weather do you think ?" +
        ", have a walk!"
private const val BAD_WEATHER_MESSAGE = "The weather not so great but" +
        ", maybe do some exercise indoors!"

class Weather : Trigger {

    private var dataProducerNeeded =  arrayOf(WEATHER_RECORDING_PUBLIC_NAME)


    override fun getDataProducerNeeded(): Array<String> {
        return dataProducerNeeded

    }

    override suspend fun shouldRunNotification(context: Context): Boolean {

        // remove the weather in shared Preference

        // make decision .. if user does not have steps for today


        return true
    }

    override suspend fun handle(context: Context, createdBy: String, data: String) {

        // store the weather in shared Preference
    }

    override fun getNotificationTitle(): String {
        return NOTIFICATION_TITLE
    }

    override fun getNotificationMessage(): String {
        return ""
    }

}