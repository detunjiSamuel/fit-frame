package com.example.contexttrigger.triggerManager

import android.content.Context
import android.util.Log
import com.example.contexttrigger.triggers.TriggerList

object TriggerController {



    fun getActiveTriggers() : Array<Trigger> {
        // TODO add logic to filter if active/inactive

        return TriggerList
    }

    suspend fun handleDataDispatch(context: Context, destination : String, data: String){

        Log.d("dev-log:TriggerController:requireHandleDataDispatch", "data received")
        Log.d("dev-log:TriggerController:requireHandleDataDispatch", destination)
        Log.d("dev-log:TriggerController:requireHandleDataDispatch", data)


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

}