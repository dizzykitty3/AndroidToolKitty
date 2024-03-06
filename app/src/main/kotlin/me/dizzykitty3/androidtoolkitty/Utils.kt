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
    private const val COM_CN = ".com.cn"
    private const val GOOGLE_MAPS = "com.google.android.apps.maps"
    private const val GOOGLE_PLAY_STORE = "com.android.vending"
    private var currentToast: Toast? = null
    private lateinit var applicationContext: Context

    @JvmStatic
    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun debugLog(logEvent: String) = Log.d("me.dizzykitty3.androidtoolkitty", logEvent)

    @JvmStatic
    fun showToast(toastText: String?) {
        if (Objects.nonNull(currentToast)) {
            currentToast!!.cancel()
        }
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
        val currentTime = LocalTime.now()
        return when (currentTime.hour) {
            in 6..11 -> "Good morning"
            in 12..17 -> "Good afternoon"
            else -> "Good evening"
        }
    }

    @JvmStatic
    fun calculateDaysPassed(): Long {
        return calculateDaysFromStartOfYear(LocalDate.now())
    }

    @JvmStatic
    fun calculateTotalDaysInYear(): Long {
        val currentDate = LocalDate.now()
        return calculateDaysFromStartOfYear(LocalDate.of(currentDate.year, 12, 31)) + 1
    }

    private fun calculateDaysFromStartOfYear(endDate: LocalDate): Long {
        val startOfYear = LocalDate.of(endDate.year, 1, 1)
        return startOfYear.until(endDate, ChronoUnit.DAYS)
    }

    @JvmStatic
    fun calculateYearProgress(): Float {
        return calculateDaysPassed().toFloat() / calculateTotalDaysInYear().toFloat()
    }

    @JvmStatic
    fun displayYearProgressPercentage(progress: Float): String {
        return (progress * 100).toString().substring(0, 4)
    }

    @JvmStatic
    fun onClearClipboardButton() {
        ClipboardUtils(applicationContext).clearClipboard()
        showToastAndRecordLog(applicationContext.getString(R.string.clipboard_cleared))
    }

    @JvmStatic
    fun onClickVisitButton(userInputUrl: String) {
        if (userInputUrl.isBlank()) return
        openUrl(processUrl(dropSpaces(userInputUrl)))
    }

    @JvmStatic
    fun dropSpaces(inputString: String): String {
        // drop spaces (including full-width)
        val result = inputString.trim().replace("\\s".toRegex(), "").lowercase()
        debugLog("string process: input = $inputString, output = $result")
        return result
    }

    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun processUrl(inputUrl: String): String {
        val prefix = "https://"
        if (inputUrl.contains(".")) return "$prefix$inputUrl"

        val suffixMap = mapOf(
            // .bg
            "remove" to ".bg",
            // .cn
            "feishu" to ".cn",
            "52pojie" to "cn",
            "360" to "cn",
            // .co.jp
            "rakuten" to ".co.jp",
            "dmm" to ".co.jp",
            // .co.ar
            "mercadolibre" to ".co.ar",
            // .com.cn
            "autohome" to COM_CN, "zol" to COM_CN, "pconline" to COM_CN,
            // .co.uk
            "dailymail" to "co.uk", "bbc" to "co.uk",
            // .ee
            "linktr" to ".ee",
            // .ir
            "shaparak" to ".ir",
            // .jp
            "livedoor" to ".jp", "nicovideo" to ".jp",
            // .la
            "hitomi" to ".la",
            // .net
            "csdn" to ".net",
            "pixiv" to ".net",
            "atlassian" to ".net",
            "cnki" to ".net",
            "doubleclick" to ".net",
            "speedtest" to ".net",
            "researchgate" to ".net",
            "behance" to ".net",
            "ali213" to ".net",
            "savefrom" to ".net",
            "cloudfront" to ".net",
            "bytedance" to ".net",
            "nhentai" to ".net",
            "daum" to ".net",
            "animeflv" to ".net",
            "jb51" to ".net",
            "manatoki215" to ".net",
            // .me
            "line" to ".me",
            // .mx
            "yts" to ".mx",
            // .nz
            "mega" to ".nz",
            // .org
            "wikipedia" to ".org",
            "telegram" to ".org",
            "archive" to ".org",
            "mozilla" to ".org",
            "e-hentai" to ".org",
            "greasyfork" to ".org",
            "coursera" to ".org",
            "craigslist" to ".org",
            // .ru
            "yandex" to ".ru",
            "mail" to ".ru",
            "dzen" to ".ru",
            "avito" to ".ru",
            "ok" to ".ru",
            "ozon" to ".ru",
            "wildberries" to ".ru",
            "gosulugi" to ".ru",
            "ya" to ".ru",
            // .so
            "notion" to ".so",
            // .to
            "zoro" to ".to",
            "1337x" to ".to",
            // .tv
            "twitch" to ".tv",
            "jable" to ".tv",
            // .us
            "zoom" to ".us",
            // .wiki
            "namu" to ".wiki",
        )

        val suffix = suffixMap[inputUrl] ?: ".com"
        return "$prefix$inputUrl$suffix"
    }

    @JvmStatic
    fun openUrl(finalUrl: String) {
        val urlIntent = Intent(Intent.ACTION_VIEW)
        urlIntent.data = Uri.parse(finalUrl)
        urlIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(urlIntent)
    }

    @JvmStatic
    fun onVisitProfile(username: String, platform: String) {
        if (username.isBlank()) return
        if (platform.isBlank()) return
        val prefix = when (platform) {
            "github" -> "https://github.com/"
            "x" -> "https://x.com/"
            else -> {
                showToastAndRecordLog("platform: \"$platform\" not supported yet")
                return
            }
        }
        openUrl("$prefix${dropSpaces(username)}")
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
        applicationContext.startActivity(intent)
    }

    @JvmStatic
    fun onClickCheckSetTimeAutomaticallyButton() {
        val contentResolver: ContentResolver = applicationContext.contentResolver
        val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
        showToast(
            if (isAutoTime == 1)
                applicationContext.getString(R.string.set_time_automatically_is_on)
            else
                applicationContext.getString(R.string.set_time_automatically_is_off)
        )
    }

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun convertUnicodeToCharacter(unicode: String): String {
        val length = unicode.length
        require(length % 4 == 0) { "The length of the input is not a multiple of 4" }
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
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid Unicode string format: ", e)
        }
    }

    @JvmStatic
    fun onClickConvertButton(unicode: String, characterField: MutableState<String>) {
        if (unicode.isBlank()) return
        try {
            val result = convertUnicodeToCharacter(unicode)
            characterField.value = result
            ClipboardUtils(applicationContext).copyTextToClipboard(result)
            showToast("$result " + applicationContext.getString(R.string.copied))
        } catch (e: Exception) {
            showToast(e.message?.ifBlank { "Unknown error occurred" })
        }
    }

    @JvmStatic
    fun onClickOpenGoogleMapsButton(latitude: String, longitude: String) {
        if (latitude.isBlank()) return
        if (longitude.isBlank()) return
        openGoogleMaps(latitude, longitude)
    }

    @JvmStatic
    fun openGoogleMaps(latitude: String, longitude: String) {
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")
        val mapIntent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        mapIntent.setPackage(GOOGLE_MAPS)
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mapIntent)
    }

    @JvmStatic
    fun openCertainAppOnPlayStore(packageName: String) {
        if (packageName.isBlank()) return

        val playStoreUri: Uri = if (packageName.contains(".")) {
            Uri.parse("market://details?id=${dropSpaces(packageName)}")
        } else {
            Uri.parse("market://search?q=${packageName.trim()}")
        }
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
        playStoreIntent.setPackage(GOOGLE_PLAY_STORE)
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(playStoreIntent)
    }

    private fun startActivity(intent: Intent) {
        if (Objects.nonNull(intent.resolveActivity(applicationContext.packageManager))) {
            applicationContext.startActivity(intent)
            return
        }
        when (intent.`package`) {
            GOOGLE_PLAY_STORE -> showToastAndRecordLog(applicationContext.getString(R.string.google_play_store_not_installed))
            GOOGLE_MAPS -> {
                showToastAndRecordLog(applicationContext.getString(R.string.google_maps_app_not_installed))
                openCertainAppOnPlayStore(GOOGLE_MAPS)
            }
        }
    }
}