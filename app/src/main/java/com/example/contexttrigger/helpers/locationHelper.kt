package com.example.contexttrigger.helpers

import android.location.Location


class locationHelper {

    fun locationToString(location: Location): String {
        return "${location.latitude},${location.longitude}"
    }

    fun stringToLocation(locationString: String): Location {
        val parts = locationString.split(",")
        val latitude = parts[0].toDouble()
        val longitude = parts[1].toDouble()
        val location = Location("")
        location.latitude = latitude
        location.longitude = longitude
        return location
    }

}