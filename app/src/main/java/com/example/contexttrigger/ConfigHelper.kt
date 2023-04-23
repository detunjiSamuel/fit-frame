package com.example.contexttrigger

import android.content.Context
import android.util.Log
import com.example.contexttrigger.triggers.STEPS_GOAL_KEY
import com.example.contexttrigger.triggers.defaultGoal

const val SHARED_PREFERENCES_NAME = "MyPrefs"
const val USER_ALLOWED_NOTIFICATION_KEY = "userAllowedNotification"
const val AUTOMATIC_STEPS_RECORDING_KEY = "automaticStepsRecording"

class ConfigHelper {

     fun togglePreference(context: Context, key: String) {

         Log.d("dev-log:ConfigHelper", "Show notification value:")
         val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(key, !sharedPref.getBoolean(key, true))
            apply()
        }
    }

     fun getPreference(context: Context, key: String): Boolean {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key, false)
    }

    fun setStepsGoal(context:Context , steps: Int) {

        Log.d("dev-log:ConfigHelper", "Set Steps Goal:")
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putInt("goalCompleted", steps)
            apply()
        }

    }

    fun getStepsGoal(context: Context) : Int
    {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPref.getInt(STEPS_GOAL_KEY, defaultGoal)
    }

}