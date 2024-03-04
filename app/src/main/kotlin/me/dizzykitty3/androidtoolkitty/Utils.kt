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
    fun showToastAndRecordLog(context: Context, event: String) {
        debugLog(event)
        showToast(context, event)
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
    fun onClearClipboardButton(context: Context) {
        ClipboardUtils(context).clearClipboard()
        showToastAndRecordLog(context, "clipboard cleared")
    }

    @JvmStatic
    fun onOpenSystemSettings(context: Context, actionName: String) {
        val intent: Intent = when (actionName) {
            "display" -> Intent(Settings.ACTION_DISPLAY_SETTINGS)
            "auto_rotate" -> Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
            "locale" -> Intent(Settings.ACTION_LOCALE_SETTINGS)
            "manage_default_apps" -> Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            "bluetooth" -> Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            "date" -> Intent(Settings.ACTION_DATE_SETTINGS)
            else -> return
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @JvmStatic
    fun onClickCheckSetTimeAutomatically(context: Context) {
        val contentResolver: ContentResolver = context.contentResolver
        val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
        showToast(
            context,
            if (isAutoTime == 1) "set time automatically is now ON" else "set time automatically is now OFF"
        )
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
        context: Context,
        unicode: String,
        characterField: MutableState<String>
    ) {
        if (unicode.isBlank()) return
        try {
            val result = convertUnicodeToCharacter(unicode)
            characterField.value = result
            ClipboardUtils(context).copyTextToClipboard(result)
            showToast(context, "$result copied")
        } catch (e: Exception) {
            showToast(context, e.message?.ifBlank { "Unknown error occurred" })
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
    fun openCertainAppOnPlayStore(context: Context, packageName: String) {
        if (packageName.isBlank()) return
        var mutablePackageName = packageName
        mutablePackageName = mutablePackageName.trim()
        mutablePackageName = mutablePackageName.replace(" ", "") // drop space
        mutablePackageName = mutablePackageName.replace("　", "") // drop full-width space
        if (!mutablePackageName.contains(".")) mutablePackageName = "com.$mutablePackageName"
        val playStoreUri = Uri.parse("market://details?id=$mutablePackageName")
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)

        if (Objects.nonNull(playStoreIntent.resolveActivity(context.packageManager))) {
            context.startActivity(playStoreIntent)
            return
        }
        showToastAndRecordLog(context, "Google Play Store app is not installed")
    }

    @JvmStatic
    fun onClickOpenGoogleMapsButton(context: Context, latitude: String, longitude: String) {
        openGoogleMaps(context, latitude.ifBlank { "0" }, longitude.ifBlank { "0" })
    }
}