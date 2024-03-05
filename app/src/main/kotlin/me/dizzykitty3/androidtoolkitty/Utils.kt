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
import java.time.LocalTime
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

    @Suppress("SpellCheckingInspection")
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
    fun greeting(): String {
        val now = LocalTime.now()
        return when (now.hour) {
            in 6..11 -> "Good morning"
            in 12..17 -> "Good afternoon"
            else -> "Good evening"
        }
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
        showToastAndRecordLog(applicationContext!!.getString(R.string.clipboard_cleared))
    }

    @JvmStatic
    fun onClickVisitButton(userInputUrl: String) {
        if (userInputUrl.isBlank()) return
        openUrl(processUrl(processString(userInputUrl)))
    }

    @JvmStatic
    fun processString(inputString: String): String {
        var outputString = inputString.trim()
        outputString = outputString.replace(" ", "") // drop spaces
        outputString = outputString.replace("　", "") // drop full-width spaces
        return outputString
    }

    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun processUrl(inputUrl: String): String {
        val builder = StringBuilder()
        val prefix = "https://"
        if (inputUrl.contains(".")) return builder.append(prefix).append(inputUrl).toString()
        val suffix: String = when (inputUrl) {
            "remove" -> ".bg"
            "feishu", "52pojie", "360" -> ".cn"
            "rakuten", "dmm" -> ".co.jp"
            "mercadolibre" -> ".com.ar"
            "autohome", "zol", "pconline" -> ".com.cn"
            "dailymail", "bbc" -> "co.uk"
            "linktr" -> ".ee"
            "shaparak" -> ".ir"
            "livedoor", "nicovideo" -> ".jp"
            "hitomi" -> ".la"
            "csdn", "pixiv", "atlassian", "cnki", "doubleclick", "speedtest", "researchgate",
            "behance", "ali213", "savefrom", "cloudfront", "bytedance", "nhentai", "daum",
            "animeflv", "jb51", "manatoki215" -> ".net"

            "line" -> ".me"
            "yts" -> ".mx"
            "mega" -> ".nz"
            "wikipedia", "telegram", "archive", "mozilla", "e-hentai", "greasyfork",
            "coursera", "craigslist" -> ".org"

            "yandex", "mail", "dzen", "avito", "ok", "ozon", "wildberries", "gosulugi",
            "ya" -> ".ru"

            "notion" -> ".so"
            "zoro", "1337x" -> ".to"
            "twitch", "jable" -> ".tv"
            "zoom" -> ".us"
            "namu" -> ".wiki"
            else -> ".com"
        }
        return builder.append(prefix).append(inputUrl).append(suffix).toString()
    }

    @JvmStatic
    fun openUrl(finalUrl: String) {
        val urlIntent = Intent(Intent.ACTION_VIEW)
        urlIntent.data = Uri.parse(finalUrl)
        urlIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        checkContextNullSafety()
        applicationContext!!.startActivity(urlIntent)
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
        showToast(
            if (isAutoTime == 1)
                applicationContext!!.getString(R.string.set_time_automatically_is_on)
            else
                applicationContext!!.getString(R.string.set_time_automatically_is_off)
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
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Invalid Unicode string format: ", e)
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
            showToast("$result " + applicationContext!!.getString(R.string.copied));
        } catch (e: Exception) {
            showToast(e.message?.ifBlank { "Unknown error occurred" })
        }
    }

    @JvmStatic
    fun onClickOpenGoogleMapsButton(latitude: String, longitude: String) {
        openGoogleMaps(latitude.ifBlank { return }, longitude.ifBlank { return })
    }

    @JvmStatic
    fun openGoogleMaps(latitude: String, longitude: String) {
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")
        val mapIntent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps") // Google Maps
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        checkContextNullSafety()
        if (Objects.nonNull(mapIntent.resolveActivity(applicationContext!!.packageManager))) {
            applicationContext?.startActivity(mapIntent)
            return
        }
        showToastAndRecordLog(applicationContext!!.getString(R.string.google_maps_app_not_installed))
        openCertainAppOnPlayStore("com.google.android.apps.maps") // Google Maps
    }

    @JvmStatic
    fun openCertainAppOnPlayStore(packageName: String) {
        if (packageName.isBlank()) return
        var mPackageName = packageName

        val playStoreUri: Uri = if (mPackageName.contains(".")) {
            mPackageName = processString(mPackageName)
            Uri.parse("market://details?id=$mPackageName")
        } else {
            mPackageName = mPackageName.trim()
            Uri.parse("market://search?q=$mPackageName")
        }
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
        playStoreIntent.setPackage("com.android.vending") // Google Play Store
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        checkContextNullSafety()
        if (Objects.nonNull(playStoreIntent.resolveActivity(applicationContext!!.packageManager))) {
            applicationContext!!.startActivity(playStoreIntent)
            return
        }
        showToastAndRecordLog(applicationContext!!.getString(R.string.google_play_store_not_installed))
    }
}