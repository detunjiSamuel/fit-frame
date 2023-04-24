package com.example.contexttrigger.triggers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.contexttrigger.triggerManager.Trigger
import com.example.contexttrigger.dataProducers.STEPS_RECORDING_PUBLIC_NAME
import com.example.contexttrigger.db.steps.AddStep
import com.example.contexttrigger.db.steps.GetSteps
import com.example.contexttrigger.helpers.TimeHelper
import com.example.contexttrigger.ui.configurations.SHARED_PREFERENCES_NAME


private var HALF_WAY_TITLE = "HalfwayPoint"
private var COMPLETED_GOAL_TITLE = "Goal Completed"

 var defaultGoal = 100
var STEPS_GOAL_KEY = "STEPS-GOAL"

class StepGoal () : Trigger {

    private var emitterNeeded =  arrayOf(STEPS_RECORDING_PUBLIC_NAME)

    private lateinit var _context : Context


    override fun getDataProducerNeeded(): Array<String> {
        return emitterNeeded
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun shouldRunNotification(context: Context): Boolean {
        _context  = context
        var steps = getStepsCompleted()
        var goal = getStepGoal()
        if (steps * 2 > goal || steps >= goal)
            return true
        return false
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun handle(context: Context, createdBy : String, data :String) {
        _context = context
        var stepsCompleted = getStepsCompleted()
        var newSteps = stepsCompleted + data.toInt()

        updateSteps(newSteps)

        Log.d(" dev-log:StepGoal" , "added")
        Log.d("dev-log:StepGoal" , newSteps.toString())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getNotificationTitle(context: Context): String {

        _context = context

        var steps = getStepsCompleted()
        var goal = getStepGoal()

        if (steps >= goal)

            return COMPLETED_GOAL_TITLE


        return HALF_WAY_TITLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getNotificationMessage(context: Context): String {

        _context = context

        var steps = getStepsCompleted()
        var goal = getStepGoal()


        if (steps >= goal)

            return "Congratulation , you did a good job . " +
                    "You completed your step goals"


       return "You are half way there with: " + getStepsCompleted().toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun updateSteps(newSteps :Int) {
        AddStep()(_context , TimeHelper().currentDate() , newSteps)
    }

    @RequiresApi(Build.VERSION_CODES.O)
     suspend fun getStepsCompleted(): Int {

        val completedSteps = GetSteps()(_context , TimeHelper().currentDate()) ?: return 0

        return completedSteps.steps

    }

    private fun getStepGoal(): Int {

        val sharedPref =
            _context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        return sharedPref.getInt(STEPS_GOAL_KEY, defaultGoal)

    }


}