package me.dizzykitty3.androidtoolkitty.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import me.dizzykitty3.androidtoolkitty.Actions
import me.dizzykitty3.androidtoolkitty.R
import java.util.Objects

class IntentUtils(private val context: Context) {
    fun openUrl(finalUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(finalUrl)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        Actions.debugLog("openUrl")
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
        context.startActivity(intent)
        Actions.debugLog("onOpenSystemSettings: $settingType")
    }

    fun openGoogleMaps(latitude: String, longitude: String) {
        val coordinates = "$latitude,$longitude"
        val googleMapsIntentUri = Uri.parse("geo:$coordinates?q=$coordinates")
        val intent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
        intent.setPackage(GOOGLE_MAPS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun startActivity(intent: Intent) {
        if (Objects.nonNull(intent.resolveActivity(context.packageManager))) {
            context.startActivity(intent)
            return
        }
        when (intent.`package`) {
            GOOGLE_PLAY_STORE ->
                ToastUtils(context).showToastAndRecordLog(
                    context.getString(R.string.google_play_store_not_installed)
                )

            GOOGLE_MAPS -> {
                ToastUtils(context).showToastAndRecordLog(
                    context.getString(R.string.google_maps_app_not_installed)
                )
                Actions.openAppOnPlayStore(GOOGLE_MAPS)
            }
        }
    }

    companion object {
        private const val GOOGLE_MAPS = "com.google.android.apps.maps"
        private const val GOOGLE_PLAY_STORE = "com.android.vending"
    }
}