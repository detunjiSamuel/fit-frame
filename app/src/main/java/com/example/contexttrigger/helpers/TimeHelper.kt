package com.example.contexttrigger.helpers

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

class TimeHelper {
    fun calculateHoursDifference(timestamp: String): Long {

        val times = timestamp.toLong()
        val currentTimestamp = System.currentTimeMillis()
        val timeDifference = currentTimestamp - times
        return timeDifference / (1000 * 60 * 60)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun currentDate(): String {
        return LocalDate.now().toString()
    }

    fun isNightTime(): Boolean {
        val currentTime = Calendar.getInstance().time
        val calendar = Calendar.getInstance().apply { time = currentTime }
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        return (hour < 6 || hour >= 18) // Night time range: from 6pm to 6am
    }
}