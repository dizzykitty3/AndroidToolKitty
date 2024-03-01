package me.dizzykitty3.androidtoolkitty

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Objects

object Utils {
    private var currentToast: Toast? = null

    @JvmStatic
    @Throws(Exception::class)
    fun convertUnicodeToCharacter(unicode: String): String {
        val length = unicode.length
        if (length % 4 != 0) throw Exception("The length of the input is not a multiple of 4")
        try {
            val stringBuilder = StringBuilder()
            var i = 0
            while (i < length) {
                val hexValue = unicode.substring(i, i + 4)
                val decimalValue = hexValue.toInt(16)
                stringBuilder.append(decimalValue.toChar())
                i += 4
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            throw Exception("Invalid input")
        }
    }

    @JvmStatic
    fun openGoogleMaps(context: Context, latitude: String, longitude: String) {
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")

        val mapIntent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        if (Objects.nonNull(mapIntent.resolveActivity(context.packageManager))) {
            context.startActivity(mapIntent)
            return
        }
        showToastAndRecordLog(context, "Google Maps app is not installed")
        openCertainAppOnPlayStore(context, "com.google.android.apps.maps")
    }

    @JvmStatic
    fun showToastAndRecordLog(context: Context, event: String) {
        debugLog(event)
        showToast(context, event)
    }

    @JvmStatic
    fun openCertainAppOnPlayStore(context: Context, packageName: String) {
        var mutablePackageName = packageName
        if (!packageName.contains(".")) mutablePackageName = "com.$packageName"
        val playStoreUri = Uri.parse("market://details?id=$mutablePackageName")
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)

        if (Objects.nonNull(playStoreIntent.resolveActivity(context.packageManager))) {
            context.startActivity(playStoreIntent)
            return
        }
        showToastAndRecordLog(context, "Google Play Store app is not installed")
    }

    @JvmStatic
    fun debugLog(logEvent: String) {
        Log.d("me.dizzykitty3.androidtoolkitty", logEvent)
    }

    @JvmStatic
    fun showToast(context: Context, toastText: String?) {
        if (Objects.nonNull(currentToast)) {
            currentToast!!.cancel()
        }
        currentToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT)
        currentToast!!.show()
    }

    @JvmStatic
    fun calculateDaysPassed(): Long {
        val currentDate = LocalDate.now()
        val startOfYear = LocalDate.of(currentDate.year, 1, 1)
        return startOfYear.until(currentDate, ChronoUnit.DAYS)
    }

    @JvmStatic
    fun calculateTotalDaysInYear(): Long {
        val currentDate = LocalDate.now()
        val startOfYear = LocalDate.of(currentDate.year, 1, 1)
        val endOfYear = LocalDate.of(currentDate.year, 12, 31)
        return startOfYear.until(endOfYear, ChronoUnit.DAYS) + 1
    }

    @JvmStatic
    fun calculateYearProgress(): Float {
        return calculateDaysPassed().toFloat() / calculateTotalDaysInYear().toFloat()
    }
}