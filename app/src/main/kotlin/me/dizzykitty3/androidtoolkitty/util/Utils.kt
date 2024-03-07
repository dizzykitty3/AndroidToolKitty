package me.dizzykitty3.androidtoolkitty.util

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.util.TextUtils.convertUnicodeToCharacter
import me.dizzykitty3.androidtoolkitty.util.TextUtils.dropSpaces
import me.dizzykitty3.androidtoolkitty.util.TextUtils.processUrl
import java.util.Objects

object Utils {
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
    fun onClearClipboardButton() {
        ClipboardUtils(applicationContext).clearClipboard()
        showToastAndRecordLog(applicationContext.getString(R.string.clipboard_cleared))
    }

    @JvmStatic
    fun onClickVisitButton(userInputUrl: String) {
        if (userInputUrl.isBlank()) return
        openUrl(processUrl(dropSpaces(userInputUrl)))
        debugLog("onClickVisitButton")
    }

    @JvmStatic
    fun openUrl(finalUrl: String) {
        val urlIntent = Intent(Intent.ACTION_VIEW)
        urlIntent.data = Uri.parse(finalUrl)
        urlIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(urlIntent)
        debugLog("openUrl")
    }

    @JvmStatic
    fun onVisitProfile(username: String, platform: String) {
        if (username.isBlank()) return
        if (platform.isBlank()) return
        val prefix = when (platform) {
            "GitHub" -> "https://github.com/"
            "Weibo 微博" -> "https://weibo.com/n/"
            "X (Twitter)" -> "https://x.com/"
            else -> {
                // TODO upload
                showToastAndRecordLog("platform: \"$username\" uploaded")
                return
            }
        }
        openUrl("$prefix${dropSpaces(username)}")
        debugLog("onVisitProfile")
    }

    @JvmStatic
    fun onOpenSystemSettings(settingType: String) {
        val intent: Intent = when (settingType) {
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
        debugLog("onOpenSystemSettings: $settingType")
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
        debugLog("onClickCheckSetTimeAutomaticallyButton")
    }

    @JvmStatic
    fun onClickConvertButton(unicode: String, characterField: MutableState<String>) {
        if (unicode.isBlank()) return
        try {
            val result = convertUnicodeToCharacter(unicode)
            characterField.value = result
            ClipboardUtils(applicationContext).copyTextToClipboard(result)
        } catch (e: Exception) {
            showToast(e.message?.ifBlank { "Unknown error occurred" })
        }
        debugLog("onClickConvertButton")
    }

    @JvmStatic
    fun onClickOpenGoogleMapsButton(latitude: String, longitude: String) {
        if (latitude.isBlank()) return
        if (longitude.isBlank()) return
        openGoogleMaps(latitude, longitude)
        debugLog("onClickOpenGoogleMapsButton")
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
        debugLog("openCertainAppOnPlayStore")
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