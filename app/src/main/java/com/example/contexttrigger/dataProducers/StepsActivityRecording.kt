package com.example.contexttrigger.dataProducers

import android.app.Service
import android.content.Intent
import android.os.IBinder


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

import android.util.Log
import com.example.contexttrigger.sensorManager.SensorController
import com.example.contexttrigger.sensorManager.SensorUpdatesHandler


const val STEPS_RECORDING_PUBLIC_NAME = "STEPS_ACTIVITY_RECORDING"


class StepsActivityRecording: Service() , SensorEventListener {


    private var totalStepsRecorded = 0 // lastRecordSteps
    private var firstRecorded = true
    private var maxBeforeCount = 50

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("dev-log:stepsActivityRecording", "steps counter started")

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
            Log.d("dev-log:stepsActivityRecording", "steps sensor activated")
        } else {
            Log.e("dev-log:stepsActivityRecording", "steps sensor not found")
            Log.e("dev-log:stepsActivityRecording", "reporting internal issue")

            val intent = Intent(this, SensorController::class.java)
            intent.putExtra("IS_REPORTING", "INTERNAL_FAILURE")
            intent.putExtra("DATA", STEPS_RECORDING_PUBLIC_NAME)
            startService(intent)
            stopSelf() // stop service
        }
        return START_STICKY
    }


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onSensorChanged(event: SensorEvent) {

        val currentCount = event.values[0].toInt() // chatGPT says it is correct

        Log.d("dev-log:stepsOnSensorChanged", "New steps: $currentCount")
        if (firstRecorded) {
            totalStepsRecorded = currentCount
            firstRecorded = false
        } else {
            val diff = currentCount - totalStepsRecorded
            if (diff > maxBeforeCount) {
                totalStepsRecorded = currentCount

                val intent  = Intent (this , SensorUpdatesHandler::class.java)

                intent.putExtra("CREATED_FOR" ,  STEPS_RECORDING_PUBLIC_NAME )

                intent.putExtra("DATA" , diff)

                startService(intent)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

}