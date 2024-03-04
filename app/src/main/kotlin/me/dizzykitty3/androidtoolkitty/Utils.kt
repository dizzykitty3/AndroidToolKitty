package me.dizzykitty3.androidtoolkitty

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Objects

object Utils {
    private var currentToast: Toast? = null
    private var applicationContext: Context? = null

    @JvmStatic
    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    private fun checkContextNullSafety() {
        checkNotNull(applicationContext) { "Context has not been initialized. Please call init() first." }
    }

    @JvmStatic
    fun debugLog(logEvent: String) {
        Log.d("me.dizzykitty3.androidtoolkitty", logEvent)
    }

    @JvmStatic
    fun showToast(toastText: String?) {
        if (Objects.nonNull(currentToast)) {
            currentToast!!.cancel()
        }
        checkContextNullSafety()
        currentToast = Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT)
        currentToast!!.show()
    }

    @JvmStatic
    fun showToastAndRecordLog(logEvent: String) {
        debugLog(logEvent)
        showToast(logEvent)
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

    @JvmStatic
    fun onClearClipboardButton() {
        checkContextNullSafety()
        ClipboardUtils(applicationContext).clearClipboard()
        showToastAndRecordLog("clipboard cleared")
    }

    @JvmStatic
    fun onOpenSystemSettings(actionName: String) {
        val intent: Intent = when (actionName) {
            "display" -> Intent(Settings.ACTION_DISPLAY_SETTINGS)
            "auto_rotate" -> Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
            "locale" -> Intent(Settings.ACTION_LOCALE_SETTINGS)
            "manage_default_apps" -> Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            "bluetooth" -> Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            "date" -> Intent(Settings.ACTION_DATE_SETTINGS)
            "ignore_battery_optimization" -> Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            else -> return
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        checkContextNullSafety()
        applicationContext?.startActivity(intent)
    }

    @JvmStatic
    fun onClickCheckSetTimeAutomaticallyButton() {
        checkContextNullSafety()
        val contentResolver: ContentResolver = applicationContext!!.contentResolver
        val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
        showToast(if (isAutoTime == 1) "set time automatically is now ON" else "set time automatically is now OFF")
    }

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
    fun onClickConvertButton(
        unicode: String,
        characterField: MutableState<String>
    ) {
        if (unicode.isBlank()) return
        try {
            val result = convertUnicodeToCharacter(unicode)
            characterField.value = result
            checkContextNullSafety()
            ClipboardUtils(applicationContext).copyTextToClipboard(result)
            showToast("$result copied")
        } catch (e: Exception) {
            showToast(e.message?.ifBlank { "Unknown error occurred" })
        }
    }

    @JvmStatic
    fun openGoogleMaps(latitude: String, longitude: String) {
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")
        val mapIntent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        checkContextNullSafety()
        if (Objects.nonNull(mapIntent.resolveActivity(applicationContext!!.packageManager))) {
            applicationContext?.startActivity(mapIntent)
            return
        }
        showToastAndRecordLog("Google Maps app is not installed")
        openCertainAppOnPlayStore("com.google.android.apps.maps")
    }

    @JvmStatic
    fun openCertainAppOnPlayStore(packageName: String) {
        if (packageName.isBlank()) return

        var mPackageName = packageName
        mPackageName = mPackageName.trim()
        mPackageName = mPackageName.replace(" ", "") // drop space
        mPackageName = mPackageName.replace("　", "") // drop full-width space
        if (!mPackageName.contains(".")) mPackageName = "com.$mPackageName"

        val playStoreUri = Uri.parse("market://details?id=$mPackageName")
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
        playStoreIntent.setPackage("com.android.vending")
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        checkContextNullSafety()
        if (Objects.nonNull(playStoreIntent.resolveActivity(applicationContext!!.packageManager))) {
            applicationContext!!.startActivity(playStoreIntent)
            return
        }
        showToastAndRecordLog("Google Play Store app is not installed")
    }

    @JvmStatic
    fun onClickOpenGoogleMapsButton(latitude: String, longitude: String) {
        openGoogleMaps(latitude.ifBlank { return }, longitude.ifBlank { return })
    }
}