package me.dizzykitty3.androidtoolkitty.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils.debugLog

class IntentUtils(private val context: Context) {
    fun openUrl(finalUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(
            if (finalUrl.contains(HTTPS))
                finalUrl
            else
                "$HTTPS$finalUrl"
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
        debugLog("openUrl")
    }

    @SuppressLint("InlinedApi")
    fun openSystemSettings(settingType: String) {
        val intent: Intent = when (settingType) {
            "display" -> Intent(Settings.ACTION_DISPLAY_SETTINGS)
            "auto_rotate" -> if (OsVersion.android12()) Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS) else return
            "locale" -> Intent(Settings.ACTION_LOCALE_SETTINGS)
            "manage_default_apps" -> Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            "bluetooth" -> Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            "date" -> Intent(Settings.ACTION_DATE_SETTINGS)
            "ignore_battery_optimization" -> Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            "development_settings" -> Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            "captioning" -> Intent(Settings.ACTION_CAPTIONING_SETTINGS)
            else -> return
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            startActivity(intent)
            debugLog("onOpenSystemSettings: $settingType")
        } catch (e: Exception) {
            ToastUtils(context).showToast(context.getString(R.string.system_settings_unsupported))
            debugLog(">>>ERROR<<< openSystemSettings: $e")
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
        } catch (e: Exception) {
            debugLog(">>>ERROR<<< openGoogleMaps: $e")
        }
    }

    private fun startActivity(intent: Intent) {
        try {
            context.startActivity(intent)
            return
        } catch (e: Exception) {
            debugLog(">>>ERROR<<< startActivity: $e")
        }

        when (intent.`package`) {
            GOOGLE_PLAY -> {
                ToastUtils(context).showToast(context.getString(R.string.google_play_not_installed))
                debugLog("Google Play not installed")
            }

            GOOGLE_MAPS -> {
                ToastUtils(context).showToast(context.getString(R.string.google_maps_not_installed))
                debugLog("Google Maps not installed")
                IntentUtils(context).openAppOnMarket(GOOGLE_MAPS)
            }
        }
    }

    fun openAppOnMarket(packageName: String, isGooglePlay: Boolean = true) {
        val marketUri: Uri = Uri.parse(
            if (packageName.isBlank()) {
                return
            } else if (packageName.contains(".")) {
                "market://details?id=${StringUtils.dropSpaces(packageName)}"
            } else {
                "market://search?q=${packageName.trim()}"
            }
        )

        val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)

        if (isGooglePlay) marketIntent.setPackage(GOOGLE_PLAY)
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            startActivity(marketIntent)
            debugLog("openAppOnMarket")
        } catch (e: Exception) {
            debugLog(">>>ERROR<<< openCertainAppOnPlayStore: $e")
        }
    }

    companion object {
        private const val HTTPS = "https://"
        private const val GOOGLE_MAPS = "com.google.android.apps.maps"
        private const val GOOGLE_PLAY = "com.android.vending"
    }
}