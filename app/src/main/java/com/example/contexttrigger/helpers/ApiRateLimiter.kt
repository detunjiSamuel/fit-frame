package com.example.contexttrigger.helpers

import android.content.Context
import com.example.contexttrigger.SHARED_PREFERENCES_NAME
import java.util.concurrent.TimeUnit

class ApiRateLimiter (private val context: Context) {

    private val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    private val rateLimitKey = "weather-api-rate-limit"
    private val rateLimitInterval = TimeUnit.HOURS.toMillis(1)  // rate limit interval in milliseconds

    fun canMakeApiRequest(): Boolean {
        val lastRequestTime = sharedPref.getLong(rateLimitKey, 0L)
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastRequestTime

        return elapsedTime >= rateLimitInterval
    }

    fun updateLastRequestTime() {
        sharedPref.edit().putLong(rateLimitKey, System.currentTimeMillis()).apply()
    }
}