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
        val gmmIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")

        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
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
        val playStoreUri = Uri.parse("market://details?id=$packageName")
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
    fun showToast(context: Context, toastText: String) {
        if (Objects.nonNull(currentToast)) {
            currentToast!!.cancel()
        }
        currentToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT)
        currentToast!!.show()
    }

    @JvmStatic
    fun calculateYearProgress(): Float {
        val currentDate = LocalDate.now()
        val startOfYear = LocalDate.of(currentDate.year, 1, 1)
        val endOfYear = LocalDate.of(currentDate.year, 12, 31)

        val totalDaysInYear = startOfYear.until(endOfYear, ChronoUnit.DAYS)
        val daysPassed = startOfYear.until(currentDate, ChronoUnit.DAYS)

        return daysPassed.toFloat() / totalDaysInYear.toFloat()
    }
}