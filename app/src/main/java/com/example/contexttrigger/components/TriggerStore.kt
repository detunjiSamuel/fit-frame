package com.example.contexttrigger.components

import android.content.Context
import android.util.Log


import com.example.contexttrigger.triggers.TriggerList

private const val NOTIFICATION_CHANNEL_ID_Event = "REGULAR-EVENT"


object TriggerStore {

    fun getAllTriggers() : Array< Trigger >{
        return TriggerList
    }

//    fun getActiveListeners(): Array<Trigger> {
//        // useless but not sure again
//        return triggers.toTypedArray()
//    }

    private fun getActiveTriggers() : Array< Trigger > {
        // TODO add logic to filter if active/inactive

        return TriggerList
    }

    suspend fun handleDataDispatch(context: Context, destination : String, data: String){

        Log.d("dev-log:TriggerStore:requireHandleDataDispatch", "data received")
        Log.d("dev-log:TriggerStore:requireHandleDataDispatch", destination)
        Log.d("dev-log:TriggerStore:requireHandleDataDispatch", data)


        /*
        * look through the triggers
        *   send data to anyone of the that have the destination in their list
        * */


        for (trigger in getActiveTriggers())
        {
            var expectedProducers = trigger.getDataProducerNeeded()

            if (expectedProducers.contains(destination))
                trigger.handle(
                    context ,
                    destination ,
                    data
                )

        }



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