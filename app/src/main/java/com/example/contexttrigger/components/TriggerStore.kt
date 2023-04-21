package com.example.contexttrigger.components

import android.content.Context
import android.util.Log




import com.example.contexttrigger.triggerSamples.HalfWayPointSample
import com.example.contexttrigger.triggerSamples.TriggerList
import com.example.contexttrigger.triggerSamples.testSample

private const val NOTIFICATION_CHANNEL_ID_Event = "REGULAR-EVENT"


object TriggerStore {

    fun getAllTriggers() : Array< Trigger >{
        return TriggerList
    }

//    fun getActiveListeners(): Array<Trigger> {
//        // useless but not sure again
//        return triggers.toTypedArray()
//    }

    fun getActiveTriggers() : Array< Trigger > {
        // TODO add logic to filter if active/inactive

        return TriggerList
    }

    fun handleDataDispatch(destination : String , data: String){

        Log.d("dev-log:TriggerStore:requireHandleDataDispatch", "data received")
        Log.d("dev-log:TriggerStore:requireHandleDataDispatch", destination)
        Log.d("dev-log:TriggerStore:requireHandleDataDispatch", data)

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