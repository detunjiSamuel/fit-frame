package com.example.contexttrigger.components.notification

import android.content.Context
import com.example.contexttrigger.components.trigger.NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE


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

                var messageReceived = trigger.getNotificationTitle()

                if (messageReceived != NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE)

                {
                    fireEvent(context , 1001,
                        NOTIFICATION_CHANNEL_ID_Event,
                        trigger.getNotificationTitle(),
                        trigger.getNotificationMessage(context)
                    )

                    continue
                }
                fireEvent(context , 1001 ,trigger.getCustomNotification(context))

            }
        }


    }

    fun makeDeviceContextDecisions(){



    }


    private fun passedBasicNotificationRules(): Boolean {
        return didUserAllowNotification() &&
                isNotNightTime() &&
                lastNotificationFarEnough()
    }

    private fun didUserAllowNotification(): Boolean {

         val sharedPref = _context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val stored = sharedPref.getString("userAllowedNotification", true.toString())

        return stored == "true"

    }

    private fun isNotNightTime(): Boolean {
        return !TimeHelper().isNightTime()
    }

    private fun lastNotificationFarEnough():Boolean {
        val sharedPref = _context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val lastTriggerNotificationTimestamp = sharedPref.getString("lastTriggerNotification", "NOT_EXIST")

        if (lastTriggerNotificationTimestamp == "NOT_EXIST")
            return true

        val minutesDifference = TimeHelper()
            .calculateMinutesDifference(lastTriggerNotificationTimestamp.toString())

        return minutesDifference > 15

    }

}