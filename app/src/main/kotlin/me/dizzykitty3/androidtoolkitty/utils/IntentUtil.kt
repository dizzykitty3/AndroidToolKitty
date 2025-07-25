package me.dizzykitty3.androidtoolkitty.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.SearchManager
import android.bluetooth.BluetoothAdapter
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_POWER_USAGE_SUMMARY
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.provider.Settings
import androidx.core.net.toUri
import me.dizzykitty3.androidtoolkitty.*
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.utils.StringUtil.dropSpaces
import me.dizzykitty3.androidtoolkitty.utils.ToastUtil.showToast
import timber.log.Timber

object IntentUtil {
    // Didn't use StartActivity as the name because a custom extension function is needed.
    private fun Context.launch(intent: Intent) {
        var msg: String

        try {
            Timber.d("startActivity")
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
            return
        } catch (e: ActivityNotFoundException) {
            Timber.e("brand = ${StringUtil.manufacturer}\nintent = ${intent}\n$e")
            msg = this.getString(R.string.oem_removed, StringUtil.manufacturer)
        }

        when (intent.`package`) {
            GOOGLE_PLAY -> {
                Timber.i("Google Play not installed")
                msg = this.getString(R.string.google_play_not_installed)
            }

            GOOGLE_MAPS -> {
                Timber.i("Google Maps not installed")
                msg = this.getString(R.string.google_maps_not_installed)
                checkOnMarket(GOOGLE_MAPS)
            }
        }

        this.showToast(msg)
    }

    fun Context.openSearch(query: String, bingSearch: Boolean) {
        if (query.isBlank()) return
        Timber.d("openSearch")
        if (bingSearch) {
            this.openURL("https://bing.com/search?q=$query")
        } else {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, query)
            this.launch(intent)
        }
    }

    fun Context.searchOnYouTube(query: String) {
        if (query.isBlank()) return
        Timber.d("searchOnYouTube")
        val intent = Intent(
            Intent.ACTION_VIEW,
            "https://youtube.com/results?search_query=$query".toUri()
        )
        this.launch(intent)
    }

    fun Context.openURL(url: String) {
        if (url.isBlank()) return
        Timber.d("openURL")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = (if (url.contains(HTTPS)) url else "$HTTPS$url").toUri()
        this.launch(intent)
    }

    fun Context.checkOnMarket(packageName: String, isGooglePlay: Boolean = true) {
        val marketUri: Uri = if (packageName.isBlank()) {
            return
        } else if (packageName.contains(".")) {
            "market://details?id=${packageName.dropSpaces()}"
        } else {
            "market://search?q=${packageName.trim()}"
        }.toUri()
        Timber.d("checkOnMarket")
        val intent = Intent(Intent.ACTION_VIEW, marketUri)
        if (isGooglePlay) intent.setPackage(GOOGLE_PLAY)
        this.launch(intent)
    }

    fun Context.checkOnGoogleMaps(latitude: String, longitude: String) {
        if (latitude.isBlank() || longitude.isBlank()) return
        Timber.d("checkOnGoogleMaps")
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = "geo:$coordinates?q=$coordinates".toUri()
        val intent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        intent.setPackage(GOOGLE_MAPS)
        this.launch(intent)
    }

    @JvmStatic
    fun Context.openSystemSettings(settingType: String) {
        val intent: Intent = when (settingType) {
            S_DISPLAY -> Intent(Settings.ACTION_DISPLAY_SETTINGS)
            S_AUTO_ROTATE -> @SuppressLint("InlinedApi") if (OSVersion.android12())
                Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS) else return

            S_BLUETOOTH -> Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            S_DEFAULT_APPS -> @SuppressLint("InlinedApi") if (OSVersion.android7())
                Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS) else return

            S_BATTERY_OPTIMIZATION -> @SuppressLint("InlinedApi") if (OSVersion.android6())
                Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS) else return

            S_CAPTION -> Intent(Settings.ACTION_CAPTIONING_SETTINGS)
            S_USAGE_ACCESS -> Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            S_OVERLAY -> @SuppressLint("InlinedApi") if (OSVersion.android6())
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION) else return

            S_MODIFY_SYSTEM -> @SuppressLint("InlinedApi") if (OSVersion.android6())
                Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS) else return

            S_LOCALE -> Intent(Settings.ACTION_LOCALE_SETTINGS)
            S_DATE -> Intent(Settings.ACTION_DATE_SETTINGS)
            S_DEVELOPER -> Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            S_ENABLE_BLUETOOTH -> Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            S_WIFI -> Intent(Settings.ACTION_WIFI_SETTINGS)
            S_BATTERY_LEVEL -> Intent(ACTION_POWER_USAGE_SUMMARY)
            S_ACCESSIBILITY -> Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            S_NOTIFICATION_LISTENER -> @SuppressLint("InlinedApi") if (OSVersion.android5Point1())
                Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS) else return

            S_DND_ACCESS -> @SuppressLint("InlinedApi") if (OSVersion.android6())
                Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS) else return

            S_UNKNOWN_APPS -> @SuppressLint("InlinedApi") if (OSVersion.android8()) Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES) else return
            S_ALARMS -> @SuppressLint("InlinedApi") if (OSVersion.android12()) Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM) else return
            S_MEDIA_MANAGEMENT -> @SuppressLint("InlinedApi") if (OSVersion.android12()) Intent(Settings.ACTION_REQUEST_MANAGE_MEDIA) else return
            S_APP_NOTIFICATIONS -> @SuppressLint("InlinedApi") if (OSVersion.android13()) Intent(Settings.ACTION_ALL_APPS_NOTIFICATION_SETTINGS) else return
            S_ACCOUNTS -> Intent(Settings.ACTION_SYNC_SETTINGS)
            S_VPN -> @SuppressLint("InlinedApi") if (OSVersion.android7()) Intent(Settings.ACTION_VPN_SETTINGS) else return
            S_SEARCH_SETTINGS -> @SuppressLint("InlinedApi") if (OSVersion.android10()) Intent(Settings.ACTION_APP_SEARCH_SETTINGS) else return
            S_SOUND -> Intent(Settings.ACTION_SOUND_SETTINGS)
            S_ABOUT_PHONE -> Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)
            S_KEYBOARD -> Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            S_NFC -> Intent((Settings.ACTION_NFC_SETTINGS))
            else -> return
        }
        Timber.d("openSystemSettings: $settingType")
        this.launch(intent)
    }

    fun Context.openAppDetailSettings() {
        Timber.d("openAppDetailSettings")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = "package:${appContext.packageName}".toUri()
        }
        this.launch(intent)
    }

    @SuppressLint("InlinedApi")
    fun Context.openAppLanguageSetting() {
        if (!OSVersion.android13()) return
        Timber.d("openAppLanguageSetting")
        val intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS)
        intent.data = Uri.fromParts(PACKAGE, this.packageName, null)
        this.launch(intent)
    }

    /**
     * Remember to use Activity Context to restart app.
     */
    @Suppress("unused")
    fun Context.restartApp() {
        Timber.d("restartApp")
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_NEW_TASK)
        this.launch(intent)
        this.finishApp()
    }

    /**
     * Remember to use Activity Context to finish app.
     */
    private fun Context.finishApp() {
        Timber.d("finishApp")
        (this as Activity).finish()
    }
}