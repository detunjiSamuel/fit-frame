package com.example.contexttrigger.components

import android.content.Context
import android.util.Log




import com.example.contexttrigger.triggerSamples.HalfWayPointSample
import com.example.contexttrigger.triggerSamples.testSample

private const val NOTIFICATION_CHANNEL_ID_Event = "REGULAR-EVENT"


object TriggerStore {
//    private val triggers = mutableListOf<Trigger>()

    // internal test
    private val triggers = arrayOf(
        HalfWayPointSample(),
        testSample()

    )

    fun registerTrigger(trigger: Trigger) {
//        triggers.add(trigger)
    }

    fun getAllTriggers() : Array< Trigger >{
//        return triggers.toTypedArray()
        return triggers
    }

//    fun getActiveListeners(): Array<Trigger> {
//        // useless but not sure again
//        return triggers.toTypedArray()
//    }

    fun getActiveTriggers() : Array< Trigger > {
        // TODO add logic to filter if active/inactive

        return triggers
    }

    fun handleDataDispatch(destination : String , data: String){

        Log.d("requireHandleDataDispatch", "data received")
        Log.d("requireHandleDataDispatch", destination)
        Log.d("requireHandleDataDispatch", data)





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