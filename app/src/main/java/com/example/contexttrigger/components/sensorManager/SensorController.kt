package com.example.contexttrigger.components.sensorManager

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.contexttrigger.dataProducers.LOCATION_RECORDING_PUBLIC_NAME
import com.example.contexttrigger.dataProducers.STEPS_RECORDING_PUBLIC_NAME
import com.example.contexttrigger.dataProducers.WEATHER_RECORDING_PUBLIC_NAME
import com.example.contexttrigger.dataProducers.dataProducerList


private var DEFAULT = "DOES NOT EXIST"

class SensorController : Service() {

    private lateinit var context : Context

    private var sensorHelper = SensorManagerHelper()


    override fun onCreate() {
        Log.d("dev-log:SensorsController", "Creating  sensor controller...")
        super.onCreate()
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("dev-log:dev-log:SensorsController", "onStartCommand Created")
        super.onStartCommand(intent, flags, startId)

        context = this@SensorController

        var bundle: Bundle? = intent?.extras

        if (bundle != null && intent != null)
        {
            if (intent.hasExtra("IS_REPORTING"))
            {

                var isReporting = intent.getStringExtra("IS_REPORTING")
                var data =  intent.getStringExtra("DATA")


                if (isReporting != null && data != null)
                {

                    Log.d("dev-log:dev-log:SensorsController", "isReporting")
                    Log.d("dev-log:dev-log:SensorsController", isReporting)
                    Log.d("dev-log:dev-log:SensorsController", data)

                    when(isReporting){
                        "PERMISSION_GRANTED" -> reportPermissions(data , true)
                        "PERMISSION_NOT_GRANTED" -> reportPermissions(data , false)
                        "INTERNAL_FAILURE" -> reportInternalFailure(data)
                        "DEVICE_RESOURCE_CHANGE" -> reportDeviceResourceChange(data)
                    }
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun reportDeviceResourceChange(data: String) {

        val parts = data.split("|")

        val resource = parts[0]

        val isAvailable = parts[1]

        val affectedProducers = getAffectedByResource(resource)


        for (producer in affectedProducers)
        {
            if (isAvailable == "YES")
                enableDataProducer(producer , resource)
            else
                disableDataProducer(producer , resource)
        }

    }

    private fun getAffectedByResource(resource: String): Array<String> {
        return when (resource) {
            "internet", "airplane-mode" -> arrayOf(LOCATION_RECORDING_PUBLIC_NAME, WEATHER_RECORDING_PUBLIC_NAME)
            else -> arrayOf()
        }

    }

    private fun reportInternalFailure(data: String) {

        disableDataProducer(data ,  "INTERNAL")

    }



    private fun reportPermissions(permission : String, isGranted  : Boolean ){


        var affectedProducers = getDataProducersAffectedByPermission(permission)

        for ( producer in  affectedProducers )
        {
            if (isGranted)
                enableDataProducer(producer , permission)
            else
                disableDataProducer(producer , permission)

        }

        // DIRECT FROM USER
        val foundProducer = dataProducerList.find { it.publicName == permission }

        if (foundProducer != null)
        {
            if (isGranted)
                enableDataProducer(permission , "user")
            else
                disableDataProducer( permission , "user")
        }

    }


    private fun getDataProducersAffectedByPermission(reason: String): Array<String> {
        return when (reason) {
            "android.permission.ACTIVITY_RECOGNITION" -> arrayOf(STEPS_RECORDING_PUBLIC_NAME)
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION" -> arrayOf(LOCATION_RECORDING_PUBLIC_NAME, WEATHER_RECORDING_PUBLIC_NAME)
            else -> arrayOf()
        }
    }

    private fun enableDataProducer(producer : String, reason: String)
    {

        val sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val stored = sharedPref.getString(sensorHelper.getDisabledProducerKey(producer), DEFAULT)


        val values = stored?.split("|")?.toMutableList()

        values?.remove(reason)

        if (values != null) {
            if (values.isEmpty()) {
                sharedPref.edit().remove(sensorHelper.getDisabledProducerKey(producer)).apply()
            } else {

                val updatedValue = values.joinToString("|")
                sharedPref.edit().putString(sensorHelper.getDisabledProducerKey(producer), updatedValue).apply()
            }
        }


    }

    private fun disableDataProducer(producer : String, reason: String){

        val sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPref.edit()

        val stored = sharedPref.getString(sensorHelper.getDisabledProducerKey(producer), DEFAULT)

        if (stored == DEFAULT)
        {
            editor.putString(sensorHelper.getDisabledProducerKey(producer), reason).apply()
        }else{
            editor.putString(sensorHelper.getDisabledProducerKey(producer), "$stored|$reason").apply()
        }
    }





}