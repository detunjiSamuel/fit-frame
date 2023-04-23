package com.example.contexttrigger.components.notification

import android.content.Context


import com.example.contexttrigger.components.trigger.TriggerController
import com.example.contexttrigger.helpers.TimeHelper
import java.util.*

private const val NOTIFICATION_CHANNEL_ID_Event = "REGULAR-EVENT"


class NotificationManagerI : Notification() {

    private lateinit var _context :Context

    // RUN NOTIFICATIONS ON BASED ON TRIGGERS
    suspend fun runRequiredNotifications(context : Context) {

        _context = context

        if (!passedBasicNotificationRules())
            return

        for (trigger in TriggerController.getActiveTriggers())
        {

            if (trigger.shouldRunNotification(context))
            {
                // REGULAR- EVENT CHANNEL HAS BEEN CREATED WITH ITS SETTINGS EARLIER
                fireEvent(context , 1001,
                    NOTIFICATION_CHANNEL_ID_Event,
                    trigger.getNotificationTitle(),
                    trigger.getNotificationMessage(context)


                )
            }
        }


    }

    fun makeDeviceContextDecisions(){



    }

    private fun passedBasicNotificationRules(): Boolean {
        return didUserAllowNotification() && isNotNightTime()
    }

    private fun didUserAllowNotification(): Boolean {

         val sharedPref = _context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val stored = sharedPref.getString("userAllowedNotification", true.toString())

        return stored == "true"

    }

    private fun isNotNightTime(): Boolean {
        return !TimeHelper().isNightTime()
    }

}