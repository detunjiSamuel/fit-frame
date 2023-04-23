package com.example.contexttrigger

import android.content.Context
import android.util.Log

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
}