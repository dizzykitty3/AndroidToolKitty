package me.dizzykitty3.androidtoolkitty.domain.utils

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
import me.dizzykitty3.androidtoolkitty.MainActivity
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.data.GOOGLE_MAPS
import me.dizzykitty3.androidtoolkitty.data.GOOGLE_PLAY
import me.dizzykitty3.androidtoolkitty.data.HTTPS
import me.dizzykitty3.androidtoolkitty.data.PACKAGE
import me.dizzykitty3.androidtoolkitty.data.SETTING_ACCESSIBILITY
import me.dizzykitty3.androidtoolkitty.data.SETTING_AUTO_ROTATE
import me.dizzykitty3.androidtoolkitty.data.SETTING_BATTERY_OPTIMIZATION
import me.dizzykitty3.androidtoolkitty.data.SETTING_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.data.SETTING_CAPTIONING
import me.dizzykitty3.androidtoolkitty.data.SETTING_DATE
import me.dizzykitty3.androidtoolkitty.data.SETTING_DEFAULT_APPS
import me.dizzykitty3.androidtoolkitty.data.SETTING_DEVELOPER
import me.dizzykitty3.androidtoolkitty.data.SETTING_DISPLAY
import me.dizzykitty3.androidtoolkitty.data.SETTING_ENABLE_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.data.SETTING_LOCALE
import me.dizzykitty3.androidtoolkitty.data.SETTING_OVERLAY
import me.dizzykitty3.androidtoolkitty.data.SETTING_POWER_USAGE_SUMMARY
import me.dizzykitty3.androidtoolkitty.data.SETTING_USAGE_ACCESS
import me.dizzykitty3.androidtoolkitty.data.SETTING_WIFI
import me.dizzykitty3.androidtoolkitty.data.SETTING_WRITE_SETTINGS
import me.dizzykitty3.androidtoolkitty.domain.utils.StringUtil.dropSpacesAndLowercase
import me.dizzykitty3.androidtoolkitty.domain.utils.ToastUtil.showToast
import timber.log.Timber

object IntentUtil {
    // Didn't use StartActivity as the name because a custom extension function is needed.
    private fun Context.launch(intent: Intent) {
        try {
            Timber.d("startActivity")
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
            return
        } catch (e: ActivityNotFoundException) {
            Timber.e(e)
            this.showToast(e.message ?: "Unsupported")
        }

        when (intent.`package`) {
            GOOGLE_PLAY -> {
                Timber.i("Google Play not installed")
                this.showToast(R.string.google_play_not_installed)
            }

            GOOGLE_MAPS -> {
                Timber.i("Google Maps not installed")
                this.showToast(R.string.google_maps_not_installed)
                checkOnMarket(GOOGLE_MAPS)
            }
        }
    }

    fun Context.openURL(url: String) {
        if (url.isBlank()) return
        Timber.d("openURL")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(if (url.contains(HTTPS)) url else "$HTTPS$url")
        this.launch(intent)
    }

    fun Context.checkOnYouTube(query: String) {
        if (query.isBlank()) return
        Timber.d("checkOnYouTube")
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/results?search_query=$query"))
        this.launch(intent)
    }

    fun Context.checkOnMarket(packageName: String, isGooglePlay: Boolean = true) {
        val marketUri: Uri = Uri.parse(
            if (packageName.isBlank()) {
                return
            } else if (packageName.contains(".")) {
                "market://details?id=${packageName.dropSpacesAndLowercase()}"
            } else {
                "market://search?q=${packageName.trim()}"
            }
        )
        Timber.d("openAppOnMarket")
        val intent = Intent(Intent.ACTION_VIEW, marketUri)
        if (isGooglePlay) intent.setPackage(GOOGLE_PLAY)
        this.launch(intent)
    }

    fun Context.checkOnGoogleMaps(latitude: String, longitude: String) {
        if (latitude.isBlank() || longitude.isBlank()) return
        Timber.d("openGoogleMaps")
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")
        val intent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        intent.setPackage(GOOGLE_MAPS)
        this.launch(intent)
    }

    fun Context.openSearch(query: String) {
        if (query.isBlank()) return
        Timber.d("openSearch")
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, query)
        this.launch(intent)
    }

    @JvmStatic
    fun Context.openSystemSettings(settingType: String) {
        val intent: Intent = when (settingType) {
            SETTING_DISPLAY -> Intent(Settings.ACTION_DISPLAY_SETTINGS)
            SETTING_AUTO_ROTATE -> @SuppressLint("InlinedApi") if (OSVersion.a12()) Intent(
                Settings.ACTION_AUTO_ROTATE_SETTINGS
            ) else return

            // TODO API requirement
            SETTING_BLUETOOTH -> Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            SETTING_DEFAULT_APPS -> Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            SETTING_BATTERY_OPTIMIZATION -> Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            SETTING_CAPTIONING -> Intent(Settings.ACTION_CAPTIONING_SETTINGS)
            SETTING_USAGE_ACCESS -> Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            SETTING_OVERLAY -> Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            SETTING_WRITE_SETTINGS -> Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            SETTING_LOCALE -> Intent(Settings.ACTION_LOCALE_SETTINGS)
            SETTING_DATE -> Intent(Settings.ACTION_DATE_SETTINGS)
            SETTING_DEVELOPER -> Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            SETTING_ENABLE_BLUETOOTH -> Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            SETTING_WIFI -> Intent(Settings.ACTION_WIFI_SETTINGS)
            SETTING_POWER_USAGE_SUMMARY -> Intent(ACTION_POWER_USAGE_SUMMARY)
            SETTING_ACCESSIBILITY -> Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            else -> return
        }
        Timber.d("onOpenSystemSettings: $settingType")
        this.launch(intent)
    }

    fun Context.openAppDetailSettings() {
        Timber.d("openPermissionPage")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(PACKAGE, appContext.packageName, null)
        intent.setData(uri)
        this.launch(intent)
    }

    /**
     * Remember to use Activity Context to restart app.
     */
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
    fun Context.finishApp() {
        Timber.d("finishApp")
        (this as Activity).finish()
    }
}