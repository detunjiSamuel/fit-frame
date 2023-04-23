package com.example.contexttrigger.sensorManager

import android.content.Context
import android.preference.PreferenceManager
import com.example.contexttrigger.SHARED_PREFERENCES_NAME
import com.example.contexttrigger.dataProducers.DataProducer
import com.example.contexttrigger.dataProducers.dataProducerList


private var DEFAULT = "NOT-EXISTS"

class SensorManagerHelper {

    // getDeviceDataProducers


    fun getActiveDataProducers(context: Context): List<DataProducer> {


        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        // Filter out disabled data producers
        val activeDataProducers = dataProducerList.filter { dataProducer ->
            val disabledKey = getDisabledProducerKey(dataProducer.publicName)
            val stored = sharedPref.getString(disabledKey,
                DEFAULT
            )

            stored != "true"

        }

        return activeDataProducers
    }

     fun getDisabledProducerKey(producer : String) : String{
        return "disabled:$producer"
    }

}