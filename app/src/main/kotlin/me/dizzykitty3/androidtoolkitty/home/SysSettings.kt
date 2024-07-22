package me.dizzykitty3.androidtoolkitty.home

import android.content.ContentResolver
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.S_ACCESSIBILITY
import me.dizzykitty3.androidtoolkitty.S_AUTO_ROTATE
import me.dizzykitty3.androidtoolkitty.S_BATTERY_OPTIMIZATION
import me.dizzykitty3.androidtoolkitty.S_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.S_CAPTIONING
import me.dizzykitty3.androidtoolkitty.S_DATE
import me.dizzykitty3.androidtoolkitty.S_DEFAULT_APPS
import me.dizzykitty3.androidtoolkitty.S_DEVELOPER
import me.dizzykitty3.androidtoolkitty.S_DISPLAY
import me.dizzykitty3.androidtoolkitty.S_LOCALE
import me.dizzykitty3.androidtoolkitty.S_OVERLAY
import me.dizzykitty3.androidtoolkitty.S_USAGE_ACCESS
import me.dizzykitty3.androidtoolkitty.S_WRITE_SETTINGS
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.SystemSettingButton
import me.dizzykitty3.androidtoolkitty.uicomponents.Tip
import me.dizzykitty3.androidtoolkitty.utils.OSVersion

@Composable
fun SysSettings(settingsViewModel: SettingsViewModel) {
    Card(R.string.system_settings, Icons.Outlined.Settings) {
        val settingsSharedPref = remember { SettingsSharedPref }

        val settings = mutableListOf(
            Setting(S_DISPLAY, R.string.open_display_settings),
            Setting(S_AUTO_ROTATE, R.string.open_auto_rotate_settings),
            Setting(S_BLUETOOTH, R.string.open_bluetooth_settings),
            Setting(S_DEFAULT_APPS, R.string.open_default_apps_settings),
            Setting(S_BATTERY_OPTIMIZATION, R.string.open_battery_optimization_settings),
            Setting(S_CAPTIONING, R.string.open_caption_preferences),
            Setting(S_USAGE_ACCESS, R.string.open_usage_access_permission),
            Setting(S_OVERLAY, R.string.open_overlay_permission),
            Setting(S_WRITE_SETTINGS, R.string.open_write_permission),
            Setting(S_ACCESSIBILITY, R.string.open_accessibility_settings),
            Setting(S_LOCALE, R.string.open_language_settings),
            Setting(S_DATE, R.string.open_date_and_time_settings),
            Setting(S_DEVELOPER, R.string.open_developer_options)
        )

        if (!OSVersion.a12()) {
            settings.remove(Setting(S_AUTO_ROTATE, R.string.open_auto_rotate_settings))
        }
        if (!OSVersion.api24()) {
            settings.remove(Setting(S_DEFAULT_APPS, R.string.open_default_apps_settings))
        }
        if (!OSVersion.api23()) {
            settings.remove(
                Setting(
                    S_BATTERY_OPTIMIZATION,
                    R.string.open_battery_optimization_settings
                )
            )
            settings.remove(Setting(S_OVERLAY, R.string.open_overlay_permission))
            settings.remove(Setting(S_WRITE_SETTINGS, R.string.open_write_permission))
        }

        val isShowSetting = remember {
            mutableStateMapOf<String, Boolean>().apply {
                settings.forEach { setting ->
                    this[setting.settingType] =
                        settingsSharedPref.getCardShowedState(setting.settingType)
                }
            }
        }

        val devMode = settingsViewModel.settings.value.devMode
        if (!checkIsAutoTime() || devMode)
            Tip(settingsViewModel, R.string.set_time_automatically_is_off_tip)

        // TODO adjust grid height
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier.height(352.dp)
        ) {
            items(settings) { setting ->
                if (isShowSetting[setting.settingType] == true) {
                    SystemSettingButton(
                        setting.settingType,
                        setting.text
                    )
                }
            }
        }
    }
}

private fun checkIsAutoTime(): Boolean {
    val contentResolver: ContentResolver = appContext.contentResolver
    val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
    return isAutoTime == 1
}

private data class Setting(val settingType: String, @StringRes val text: Int)