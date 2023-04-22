package com.example.contexttrigger.helpers

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class TimeHelper {
    fun calculateHoursDifference(timestamp: String): Long {

        val times = timestamp.toLong()
        val currentTimestamp = System.currentTimeMillis()
        val timeDifference = currentTimestamp - times
        return timeDifference / (1000 * 60 * 60)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun CurrentDate(): String {
        return LocalDate.now().toString()
    }
}