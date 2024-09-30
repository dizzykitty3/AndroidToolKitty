package me.dizzykitty3.androidtoolkitty.utils

import android.location.Location

object LocationUtil {
    private fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0]
    }
}