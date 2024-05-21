package me.dizzykitty3.androidtoolkitty.foundation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.SearchManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_POWER_USAGE_SUMMARY
import android.net.Uri
import android.provider.Settings
import me.dizzykitty3.androidtoolkitty.MainActivity
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.const.GOOGLE_MAPS
import me.dizzykitty3.androidtoolkitty.foundation.const.GOOGLE_PLAY
import me.dizzykitty3.androidtoolkitty.foundation.const.HTTPS
import me.dizzykitty3.androidtoolkitty.foundation.const.PACKAGE
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_1
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_10
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_11
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_12
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_2
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_3
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_4
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_5
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_6
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_7
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_8
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_9
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_ENABLE_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_POWER_USAGE_SUMMARY
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_WIFI
import timber.log.Timber

object IntentUtil {
    private fun startActivity(intent: Intent, context: Context) {
        try {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Timber.d("startActivity")
            return
        } catch (e: Exception) {
            ToastUtil.toast(e.message ?: "Unknown error")
            Timber.e(">>>ERROR<<< startActivity", e)
        }

        when (intent.`package`) {
            GOOGLE_PLAY -> {
                ToastUtil.toast(R.string.google_play_not_installed)
                Timber.i("Google Play not installed")
            }

            GOOGLE_MAPS -> {
                ToastUtil.toast(R.string.google_maps_not_installed)
                Timber.i("Google Maps not installed")
                checkOnMarket(GOOGLE_MAPS, context)
            }
        }
    }

    fun openURL(url: String, context: Context) {
        if (url.isBlank()) return

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(if (url.contains(HTTPS)) url else "$HTTPS$url")
        startActivity(intent, context)
        Timber.d("openURL")
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

        val intent = Intent(Intent.ACTION_VIEW, marketUri)
        if (isGooglePlay) intent.setPackage(GOOGLE_PLAY)
        startActivity(intent, context)
        Timber.d("openAppOnMarket")
    }

    fun checkOnGoogleMaps(latitude: String, longitude: String, context: Context) {
        if (latitude.isBlank() || longitude.isBlank()) return

        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")
        val intent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        intent.setPackage(GOOGLE_MAPS)
        startActivity(intent, context)
        Timber.d("openGoogleMaps")
    }

    fun openSearch(query: String, context: Context) {
        if (query.isBlank()) return

        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, query)
        startActivity(intent, context)
        Timber.d("openSearch")
    }

    @JvmStatic
    fun openSystemSettings(settingType: String, context: Context) {
        val intent: Intent = when (settingType) {
            SETTING_1 -> Intent(Settings.ACTION_DISPLAY_SETTINGS)
            SETTING_2 -> @SuppressLint("InlinedApi") if (OSVersion.android12()) Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS) else return
            SETTING_3 -> Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            SETTING_4 -> Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            SETTING_5 -> Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            SETTING_6 -> Intent(Settings.ACTION_CAPTIONING_SETTINGS)
            SETTING_7 -> Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            SETTING_8 -> Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            SETTING_9 -> Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            SETTING_10 -> Intent(Settings.ACTION_LOCALE_SETTINGS)
            SETTING_11 -> Intent(Settings.ACTION_DATE_SETTINGS)
            SETTING_12 -> Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            SETTING_ENABLE_BLUETOOTH -> Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            SETTING_WIFI -> Intent(Settings.ACTION_WIFI_SETTINGS)
            SETTING_POWER_USAGE_SUMMARY -> Intent(ACTION_POWER_USAGE_SUMMARY)
            else -> return
        }
        startActivity(intent, context)
        Timber.d("onOpenSystemSettings: $settingType")
    }

    fun openAppDetailSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(PACKAGE, appContext.packageName, null)
        intent.setData(uri)
        startActivity(intent, context)
        Timber.d("openPermissionPage")
    }

    /**
     * Remember to use Activity Context to restart app.
     */
    fun restartApp(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        Timber.d("restartApp")
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