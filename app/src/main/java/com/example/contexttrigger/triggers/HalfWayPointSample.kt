package com.example.contexttrigger.triggers

import android.content.Context
import android.util.Log
import com.example.contexttrigger.components.Trigger
import com.example.contexttrigger.dataProducers.STEPS_RECORDING_PUBLIC_NAME


class HalfWayPointSample () : Trigger {

    private var emitterNeeded =  arrayOf(STEPS_RECORDING_PUBLIC_NAME)

    private var defaultGoal = 100

    private var notificatonTitle = "HalfwayPoint"

    private lateinit var _context : Context


    override fun getEmitterNeeded(): Array<String> {
        return emitterNeeded
    }

    override suspend fun shouldRunNotification(context: Context): Boolean {

        _context  = context

        var steps = getStepsCompleted()

        if (steps > 50) {            // lazy logic
            updateSteps((steps / 2).toInt())
            return true
        }

        return false



    }


    override suspend fun handle(context: Context, createdBy : String , data :String) {

        _context = context


        var stepsCompleted = getStepsCompleted()

        var newSteps = stepsCompleted + data.toInt()

        updateSteps(newSteps)

        Log.d("requireHalfWayPoint" , "added")
        Log.d("requireHalfWayPoint" , newSteps.toString())


    }

    override fun getNotificationTitle(): String {
        return notificatonTitle
    }

    override fun getNotificationMessage(): String {
       return "You are half way there with: " + getStepsCompleted().toString()
    }

    private fun updateSteps(newSteps :Int) {
        val sharedPref = _context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        with(sharedPref.edit()) {
            putInt("goalCompleted", newSteps)
            apply()
        }

    }

    private fun getStepsCompleted(): Int {

        val sharedPref = _context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val goal = sharedPref.getInt("goalCompleted", -1)

        if (goal != -1)
            return goal


        updateSteps(0)


        return 0

    }


}