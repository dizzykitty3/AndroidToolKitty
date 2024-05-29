package me.dizzykitty3.androidtoolkitty.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.SearchManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_POWER_USAGE_SUMMARY
import android.net.Uri
import android.provider.Settings
import me.dizzykitty3.androidtoolkitty.GOOGLE_MAPS
import me.dizzykitty3.androidtoolkitty.GOOGLE_PLAY
import me.dizzykitty3.androidtoolkitty.HTTPS
import me.dizzykitty3.androidtoolkitty.PACKAGE
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SETTING_AUTO_ROTATE
import me.dizzykitty3.androidtoolkitty.SETTING_BATTERY_OPTIMIZATION
import me.dizzykitty3.androidtoolkitty.SETTING_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.SETTING_CAPTIONING
import me.dizzykitty3.androidtoolkitty.SETTING_DATE
import me.dizzykitty3.androidtoolkitty.SETTING_DEFAULT_APPS
import me.dizzykitty3.androidtoolkitty.SETTING_DEVELOPER
import me.dizzykitty3.androidtoolkitty.SETTING_DISPLAY
import me.dizzykitty3.androidtoolkitty.SETTING_ENABLE_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.SETTING_LOCALE
import me.dizzykitty3.androidtoolkitty.SETTING_OVERLAY
import me.dizzykitty3.androidtoolkitty.SETTING_POWER_USAGE_SUMMARY
import me.dizzykitty3.androidtoolkitty.SETTING_USAGE_ACCESS
import me.dizzykitty3.androidtoolkitty.SETTING_WIFI
import me.dizzykitty3.androidtoolkitty.SETTING_WRITE_SETTINGS
import me.dizzykitty3.androidtoolkitty.app_components.MainActivity
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext
import timber.log.Timber

object IntentUtil {
    private fun startActivity(intent: Intent, context: Context) {
        try {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Timber.d("startActivity")
            context.startActivity(intent)
            return
        } catch (e: Exception) {
            Timber.e(e)
            ToastUtil.toast(e.message ?: "Unknown error")
        }

        when (intent.`package`) {
            GOOGLE_PLAY -> {
                Timber.i("Google Play not installed")
                ToastUtil.toast(R.string.google_play_not_installed)
            }

            GOOGLE_MAPS -> {
                Timber.i("Google Maps not installed")
                ToastUtil.toast(R.string.google_maps_not_installed)
                checkOnMarket(GOOGLE_MAPS, context)
            }
        }
    }

    fun openURL(url: String, context: Context) {
        if (url.isBlank()) return
        Timber.d("openURL")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(if (url.contains(HTTPS)) url else "$HTTPS$url")
        startActivity(intent, context)
    }

    fun checkOnYouTube(query: String, context: Context) {
        if (query.isBlank()) return
        Timber.d("checkOnYouTube")
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/results?search_query=$query"))
        startActivity(intent, context)
    }

    fun checkOnMarket(packageName: String, context: Context, isGooglePlay: Boolean = true) {
        val marketUri: Uri = Uri.parse(
            if (packageName.isBlank()) {
                return
            } else if (packageName.contains(".")) {
                "market://details?id=${StringUtil.dropSpaces(packageName)}"
            } else {
                "market://search?q=${packageName.trim()}"
            }
        )
        Timber.d("openAppOnMarket")
        val intent = Intent(Intent.ACTION_VIEW, marketUri)
        if (isGooglePlay) intent.setPackage(GOOGLE_PLAY)
        startActivity(intent, context)
    }

    fun checkOnGoogleMaps(latitude: String, longitude: String, context: Context) {
        if (latitude.isBlank() || longitude.isBlank()) return
        Timber.d("openGoogleMaps")
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")
        val intent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        intent.setPackage(GOOGLE_MAPS)
        startActivity(intent, context)
    }

    fun openSearch(query: String, context: Context) {
        if (query.isBlank()) return
        Timber.d("openSearch")
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, query)
        startActivity(intent, context)
    }

    @JvmStatic
    fun openSystemSettings(settingType: String, context: Context) {
        val intent: Intent = when (settingType) {
            SETTING_DISPLAY -> Intent(Settings.ACTION_DISPLAY_SETTINGS)
            SETTING_AUTO_ROTATE -> @SuppressLint("InlinedApi") if (OSVersion.android12()) Intent(
                Settings.ACTION_AUTO_ROTATE_SETTINGS
            ) else return

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
            else -> return
        }
        Timber.d("onOpenSystemSettings: $settingType")
        startActivity(intent, context)
    }

    fun openAppDetailSettings(context: Context) {
        Timber.d("openPermissionPage")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(PACKAGE, appContext.packageName, null)
        intent.setData(uri)
        startActivity(intent, context)
    }

    /**
     * Remember to use Activity Context to restart app.
     */
    fun restartApp(context: Context) {
        Timber.d("restartApp")
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        finishApp(context)
    }

    /**
     * Remember to use Activity Context to finish app.
     */
    fun finishApp(context: Context) {
        Timber.d("finishApp")
        (context as Activity).finish()
    }
}