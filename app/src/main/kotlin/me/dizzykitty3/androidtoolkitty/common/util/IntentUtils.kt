package me.dizzykitty3.androidtoolkitty.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils.debugLog
import java.util.Objects

class IntentUtils(private val c: Context) {
    fun openUrl(finalUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(finalUrl)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        c.startActivity(intent)
        debugLog("openUrl")
    }

    fun openSystemSettings(settingType: String) {
        val intent: Intent = when (settingType) {
            "display" -> Intent(Settings.ACTION_DISPLAY_SETTINGS)
            "auto_rotate" -> Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
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
        c.startActivity(intent)
        debugLog("onOpenSystemSettings: $settingType")
    }

    fun openGoogleMaps(latitude: String, longitude: String) {
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")
        val intent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        intent.setPackage(GOOGLE_MAPS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        c.startActivity(intent)
    }

    private fun startActivity(intent: Intent) {
        if (Objects.nonNull(intent.resolveActivity(c.packageManager))) {
            c.startActivity(intent)
            return
        }
        when (intent.`package`) {
            GOOGLE_PLAY_STORE ->
                ToastUtils(c).showToastAndRecordLog(
                    c.getString(R.string.google_play_store_not_installed)
                )

            GOOGLE_MAPS -> {
                ToastUtils(c).showToastAndRecordLog(
                    c.getString(R.string.google_maps_app_not_installed)
                )
                IntentUtils(c).openAppOnPlayStore(GOOGLE_MAPS)
            }
        }
    }

    fun openAppOnPlayStore(packageName: String) {
        val playStoreUri: Uri = if (packageName.isBlank()) {
            Uri.parse("market://details?id=$GOOGLE_CHROME")
        } else if (packageName.contains(".")) {
            Uri.parse("market://details?id=${StringUtils.dropSpaces(packageName)}")
        } else {
            Uri.parse("market://search?q=${packageName.trim()}")
        }
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
        playStoreIntent.setPackage(GOOGLE_PLAY_STORE)
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        IntentUtils(c).startActivity(playStoreIntent)
        debugLog("openCertainAppOnPlayStore")
    }

    companion object {
        private const val GOOGLE_MAPS = "com.google.android.apps.maps"
        private const val GOOGLE_PLAY_STORE = "com.android.vending"
        private const val GOOGLE_CHROME = "com.android.chrome"
    }
}