package com.example.contexttrigger.notificationManager

import android.content.Context

import com.example.contexttrigger.triggerManager.NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE


import com.example.contexttrigger.triggerManager.TriggerController
import com.example.contexttrigger.helpers.TimeHelper
import com.example.contexttrigger.ui.configurations.SHARED_PREFERENCES_NAME
import com.example.contexttrigger.ui.configurations.USER_ALLOWED_NOTIFICATION_KEY

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

                var messageReceived = trigger.getNotificationTitle(context)

                if ( messageReceived != NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE
                    && sameMessageNotSentRecently(messageReceived))

                {
                    fireEvent(context , 1001,
                        NOTIFICATION_CHANNEL_ID_Event,
                        trigger.getNotificationTitle(context),
                        trigger.getNotificationMessage(context)
                    )

                    registerNotificationMessage(trigger.getNotificationTitle(context))

                    registerSentNotification()

                    continue
                }

                fireEvent(context , 1001 ,trigger.getCustomNotification(context))

                registerSentNotification()

            }
        }


    }



    private fun passedBasicNotificationRules(): Boolean {
        return didUserAllowNotification() &&
                isNotNightTime() &&
                lastNotificationFarEnough()
    }

    private fun didUserAllowNotification(): Boolean {

        val sharedPref = _context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(USER_ALLOWED_NOTIFICATION_KEY, true)

    }


    private fun sameMessageNotSentRecently(message : String):Boolean{

        val sharedPref = _context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val lastTimestamp = sharedPref.getString("sent:$message", "NOT_EXIST")

        if (lastTimestamp == "NOT_EXIST")
            return true

        val minutesDifference = TimeHelper()
            .calculateMinutesDifference(lastTimestamp.toString())


        return minutesDifference > 5
    }

    private fun registerNotificationMessage(message: String){

        val sharedPref = _context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val currentTimestamp = System.currentTimeMillis().toString()

        with(sharedPref.edit()) {
            putString("sent:$message", currentTimestamp)
            apply()
        }

    }


    private fun isNotNightTime(): Boolean {
        return !TimeHelper().isNightTime()
    }

    private fun lastNotificationFarEnough():Boolean {
        val sharedPref = _context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val defaultResult = "NOT_EXIST"
        val lastTriggerNotificationTimestamp = sharedPref.getString("lastTriggerNotification",defaultResult )

        if (lastTriggerNotificationTimestamp == defaultResult)
            return true

        val minutesDifference = TimeHelper()
            .calculateMinutesDifference(lastTriggerNotificationTimestamp.toString())

        return minutesDifference > 3

    }

    private fun registerSentNotification() {

        val sharedPref = _context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("lastTriggerNotification",System.currentTimeMillis().toString())
            apply()
        }

    }

}