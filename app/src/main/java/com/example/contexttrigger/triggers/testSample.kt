package com.example.contexttrigger.triggers

import android.content.Context
import android.util.Log
import com.example.contexttrigger.components.trigger.Trigger
import com.example.contexttrigger.dataProducers.LOCATION_RECORDING_PUBLIC_NAME


/***
 * IGNORE I JUST NEEDED IT FOR TEST
 */

class testSample : Trigger {

    private var emitterNeeded =  arrayOf(LOCATION_RECORDING_PUBLIC_NAME)

    override fun getDataProducerNeeded(): Array<String> {


        return emitterNeeded

    }

    override suspend fun shouldRunNotification(context: Context): Boolean {

        return false

    }

    override suspend fun handle(context: Context, createdBy: String, data: String) {

        Log.d( "dev-log:testSample" , "handler called" )
        Log.d( "dev-log:testSample" , createdBy )
        Log.d( "dev-log:testSample" , data )


    }

    override fun getNotificationTitle(): String {

        return "something"
    }

    override fun getNotificationMessage(): String {

        return "something message"
    }
}