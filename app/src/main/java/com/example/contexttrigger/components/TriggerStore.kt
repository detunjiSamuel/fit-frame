package com.example.contexttrigger.components

import android.content.Context

private const val NOTIFICATION_CHANNEL_ID_Event = "REGULAR-EVENT"


object TriggerStore {
    private val triggers = mutableListOf<Trigger>()

    fun registerTrigger(trigger: Trigger) {
        triggers.add(trigger)
    }

    fun getAllTriggers(): Array<Trigger> {
        return triggers.toTypedArray()
    }

    fun getActiveListeners(): Array<Trigger> {
        // useless but not sure again
        return triggers.toTypedArray()
    }

    fun getActiveTriggers(): Array<Trigger> {
        // TODO add logic to filter if active/inactive
        return triggers.toTypedArray()
    }

    fun handleDataDispatch(destination : String , data: String){


    }

     suspend fun runNotifications(context : Context) {

        for (trigger in getActiveTriggers())
        {

            if (trigger.shouldRunNotification(context))
            {
                // REGULAR- EVENT CHANNEL HAS BEEN CREATED WITH ITS SETTINGS EARLIER
                Notification().fireEvent(context , 1001,
                    NOTIFICATION_CHANNEL_ID_Event,
                    trigger.getNotificationTitle(),
                    trigger.getNotificationMessage()


                )
            }
        }


    }
}