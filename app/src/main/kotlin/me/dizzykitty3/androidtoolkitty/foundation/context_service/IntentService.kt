package me.dizzykitty3.androidtoolkitty.foundation.context_service

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.utils.OsVersion
import me.dizzykitty3.androidtoolkitty.foundation.utils.TString

class IntentService(private val context: Context) {
    companion object {
        private const val TAG = "IntentService"
        private const val HTTPS = "https://"
        private const val GOOGLE_MAPS = "com.google.android.apps.maps"
        private const val GOOGLE_PLAY = "com.android.vending"
        private const val SETTING_1 = "setting_display"
        private const val SETTING_2 = "setting_auto_rotate"
        private const val SETTING_3 = "setting_bluetooth"
        private const val SETTING_4 = "setting_default_apps"
        private const val SETTING_5 = "setting_battery_optimization"
        private const val SETTING_6 = "setting_caption"
        private const val SETTING_7 = "setting_locale"
        private const val SETTING_8 = "setting_date_and_time"
        private const val SETTING_9 = "setting_developer"
        private const val PACKAGE = "package"

        @Suppress("SpellCheckingInspection")
        private const val ANDROID_TOOLKITTY = "me.dizzykitty3.androidtoolkitty"
    }

    fun openUrl(finalUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(if (finalUrl.contains(HTTPS)) finalUrl else "$HTTPS$finalUrl")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
        Log.d(TAG, "openUrl")
    }

    @SuppressLint("InlinedApi")
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
            ToastService(context).toast(context.getString(R.string.system_settings_unsupported))
            Log.e(TAG, ">>>ERROR<<< openSystemSettings: $e")
        }
    }

    fun openPermissionPage() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(PACKAGE, ANDROID_TOOLKITTY, null)
        intent.setData(uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            startActivity(intent)
            Log.d(TAG, "openPermissionPage")
        } catch (e: Exception) {
            ToastService(context).toast(context.getString(R.string.system_settings_unsupported))
            Log.e(TAG, ">>>ERROR<<< openPermissionPage: $e")
        }
    }

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

    private fun startActivity(intent: Intent) {
        try {
            context.startActivity(intent)
            Log.d(TAG, "startActivity")
            return
        } catch (e: Exception) {
            Log.e(TAG, ">>>ERROR<<< startActivity: $e")
        }

        when (intent.`package`) {
            GOOGLE_PLAY -> {
                ToastService(context).toast(context.getString(R.string.google_play_not_installed))
                Log.i(TAG, "Google Play not installed")
            }

            GOOGLE_MAPS -> {
                ToastService(context).toast(context.getString(R.string.google_maps_not_installed))
                Log.i(TAG, "Google Maps not installed")
                IntentService(context).openAppOnMarket(GOOGLE_MAPS)
            }
        }
    }

    fun openAppOnMarket(packageName: String, isGooglePlay: Boolean = true) {
        val marketUri: Uri = Uri.parse(
            if (packageName.isBlank()) {
                return
            } else if (packageName.contains(".")) {
                "market://details?id=${TString.dropSpaces(packageName)}"
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
}