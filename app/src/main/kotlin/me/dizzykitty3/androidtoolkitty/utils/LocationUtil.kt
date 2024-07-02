package me.dizzykitty3.androidtoolkitty.utils

import android.location.Location
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref

object LocationUtil {
    private fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0]
    }

    fun getDistance(lat: Double, lon: Double): Float = getDistance(
        lat,
        lon,
        SettingsSharedPref.savedLatitude.toDouble(),
        SettingsSharedPref.savedLongitude.toDouble()
    )
}