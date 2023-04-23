package com.example.contexttrigger.components.notification

import android.content.Context


import com.example.contexttrigger.components.trigger.TriggerController

private const val NOTIFICATION_CHANNEL_ID_Event = "REGULAR-EVENT"


class NotificationManagerI : Notification() {

    // RUN NOTIFICATIONS ON BASED ON TRIGGERS
    suspend fun runRequiredNotifications(context : Context) {

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

        // global decision - user preference

        // there should be at least 30 minutes internal two triggers



    }

}