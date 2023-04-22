package com.example.contexttrigger.components.notification

import android.content.Context


import com.example.contexttrigger.components.trigger.TriggerStore

private const val NOTIFICATION_CHANNEL_ID_Event = "REGULAR-EVENT"


class NotificationManagerI : Notification() {

    // RUN NOTIFICATIONS ON BASED ON TRIGGERS
    suspend fun runRequiredNotifications(context : Context) {

        for (trigger in TriggerStore.getActiveTriggers())
        {

            if (trigger.shouldRunNotification(context))
            {
                // REGULAR- EVENT CHANNEL HAS BEEN CREATED WITH ITS SETTINGS EARLIER
                fireEvent(context , 1001,
                    NOTIFICATION_CHANNEL_ID_Event,
                    trigger.getNotificationTitle(),
                    trigger.getNotificationMessage()


                )
            }
        }


    }
}