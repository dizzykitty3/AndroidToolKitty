package me.dizzykitty3.androidtoolkitty.foundation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import me.dizzykitty3.androidtoolkitty.MainActivity
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKittyApp.Companion.app
import me.dizzykitty3.androidtoolkitty.foundation.const.GOOGLE_MAPS
import me.dizzykitty3.androidtoolkitty.foundation.const.GOOGLE_PLAY
import me.dizzykitty3.androidtoolkitty.foundation.const.HTTPS
import me.dizzykitty3.androidtoolkitty.foundation.const.PACKAGE
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_1
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_2
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_3
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_4
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_5
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_6
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_7
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_8
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_9

object IntentUtil {
    private const val TAG = "IntentService"

    @JvmStatic
    fun openUrl(finalUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(if (finalUrl.contains(HTTPS)) finalUrl else "$HTTPS$finalUrl")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
        Log.d(TAG, "openUrl")
    }

    @SuppressLint("InlinedApi")
    @JvmStatic
    fun openSystemSettings(settingType: String) {
        val intent: Intent = when (settingType) {
            SETTING_1 -> Intent(Settings.ACTION_DISPLAY_SETTINGS)
            SETTING_2 -> if (OsVersion.android12()) Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS) else return
            SETTING_3 -> Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            SETTING_4 -> Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            SETTING_5 -> Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            SETTING_6 -> Intent(Settings.ACTION_CAPTIONING_SETTINGS)
            SETTING_7 -> Intent(Settings.ACTION_LOCALE_SETTINGS)
            SETTING_8 -> Intent(Settings.ACTION_DATE_SETTINGS)
            SETTING_9 -> Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            else -> return
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            startActivity(intent)
            Log.d(TAG, "onOpenSystemSettings: $settingType")
        } catch (e: Exception) {
            ToastUtil.toast(app.getString(R.string.system_settings_unsupported))
            Log.e(TAG, ">>>ERROR<<< openSystemSettings: $e")
        }
    }

    @JvmStatic
    fun openPermissionPage() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(PACKAGE, app.packageName, null)
        intent.setData(uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            startActivity(intent)
            Log.d(TAG, "openPermissionPage")
        } catch (e: Exception) {
            ToastUtil.toast(app.getString(R.string.system_settings_unsupported))
            Log.e(TAG, ">>>ERROR<<< openPermissionPage: $e")
        }
    }

    @JvmStatic
    fun openGoogleMaps(latitude: String, longitude: String) {
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")

        val intent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        intent.setPackage(GOOGLE_MAPS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            startActivity(intent)
            Log.d(TAG, "openGoogleMaps")
        } catch (e: Exception) {
            Log.e(TAG, ">>>ERROR<<< openGoogleMaps: $e")
        }
    }

    @JvmStatic
    private fun startActivity(intent: Intent) {
        try {
            app.startActivity(intent)
            Log.d(TAG, "startActivity")
            return
        } catch (e: Exception) {
            Log.e(TAG, ">>>ERROR<<< startActivity: $e")
        }

        when (intent.`package`) {
            GOOGLE_PLAY -> {
                ToastUtil.toast(app.getString(R.string.google_play_not_installed))
                Log.i(TAG, "Google Play not installed")
            }

            GOOGLE_MAPS -> {
                ToastUtil.toast(app.getString(R.string.google_maps_not_installed))
                Log.i(TAG, "Google Maps not installed")
                openAppOnMarket(GOOGLE_MAPS)
            }
        }
    }

    @JvmStatic
    fun openAppOnMarket(packageName: String, isGooglePlay: Boolean = true) {
        val marketUri: Uri = Uri.parse(
            if (packageName.isBlank()) {
                return
            } else if (packageName.contains(".")) {
                "market://details?id=${StringUtil.dropSpaces(packageName)}"
            } else {
                "market://search?q=${packageName.trim()}"
            }
        )

        val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
        if (isGooglePlay) marketIntent.setPackage(GOOGLE_PLAY)
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            startActivity(marketIntent)
            Log.d(TAG, "openAppOnMarket")
        } catch (e: Exception) {
            Log.e(TAG, ">>>ERROR<<< openCertainAppOnPlayStore: $e")
        }
    }

    @JvmStatic
    fun restartApp(context: Context) {
        val intent = Intent(app, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(intent)
        (context as Activity).finish()
    }
}