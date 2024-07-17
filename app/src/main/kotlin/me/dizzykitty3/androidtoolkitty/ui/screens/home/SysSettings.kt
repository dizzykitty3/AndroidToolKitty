package me.dizzykitty3.androidtoolkitty.ui.screens.home

import android.content.ContentResolver
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.data.SETTING_ACCESSIBILITY
import me.dizzykitty3.androidtoolkitty.data.SETTING_AUTO_ROTATE
import me.dizzykitty3.androidtoolkitty.data.SETTING_BATTERY_OPTIMIZATION
import me.dizzykitty3.androidtoolkitty.data.SETTING_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.data.SETTING_CAPTIONING
import me.dizzykitty3.androidtoolkitty.data.SETTING_DATE
import me.dizzykitty3.androidtoolkitty.data.SETTING_DEFAULT_APPS
import me.dizzykitty3.androidtoolkitty.data.SETTING_DEVELOPER
import me.dizzykitty3.androidtoolkitty.data.SETTING_DISPLAY
import me.dizzykitty3.androidtoolkitty.data.SETTING_LOCALE
import me.dizzykitty3.androidtoolkitty.data.SETTING_OVERLAY
import me.dizzykitty3.androidtoolkitty.data.SETTING_USAGE_ACCESS
import me.dizzykitty3.androidtoolkitty.data.SETTING_WRITE_SETTINGS
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.SystemSettingButton
import me.dizzykitty3.androidtoolkitty.ui.components.Tip
import me.dizzykitty3.androidtoolkitty.ui.viewmodel.SettingsViewModel

@Composable
fun SysSettings(settingsViewModel: SettingsViewModel) {
    Card(R.string.system_settings, Icons.Outlined.Settings) {
        val settingsSharedPref = remember { SettingsSharedPref }

        // TODO API requirement
        val settings = listOf(
            Setting(SETTING_DISPLAY, R.string.open_display_settings),
            Setting(SETTING_AUTO_ROTATE, R.string.open_auto_rotate_settings), // android 12
            Setting(SETTING_BLUETOOTH, R.string.open_bluetooth_settings),
            Setting(SETTING_DEFAULT_APPS, R.string.open_default_apps_settings), // api 24
            Setting(
                SETTING_BATTERY_OPTIMIZATION,
                R.string.open_battery_optimization_settings
            ), // api 23
            Setting(SETTING_CAPTIONING, R.string.open_caption_preferences),
            Setting(SETTING_USAGE_ACCESS, R.string.open_usage_access_permission),
            Setting(SETTING_OVERLAY, R.string.open_overlay_permission), // api 23
            Setting(SETTING_WRITE_SETTINGS, R.string.open_write_permission), // api 23
            Setting(SETTING_ACCESSIBILITY, R.string.open_accessibility_settings),
            Setting(SETTING_LOCALE, R.string.open_language_settings),
            Setting(SETTING_DATE, R.string.open_date_and_time_settings),
            Setting(SETTING_DEVELOPER, R.string.open_developer_options)
        )

        val isShowSetting = remember {
            mutableStateMapOf<String, Boolean>().apply {
                settings.forEach { setting ->
                    this[setting.settingType] =
                        settingsSharedPref.getCardShowedState(setting.settingType)
                }
            }
        }

        val devMode = settingsViewModel.settings.value.devMode

        if (!checkIsAutoTime() || devMode) Tip(
            settingsViewModel,
            R.string.set_time_automatically_is_off_tip
        )

        Column(Modifier.horizontalScroll(rememberScrollState())) {
            Row {
                settings.subList(0, 2).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            setting.settingType,
                            setting.text
                        )
                    }
                }
            }
            Row {
                settings.subList(2, 4).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            setting.settingType,
                            setting.text
                        )
                    }
                }
            }
            Row {
                settings.subList(4, 6).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            setting.settingType,
                            setting.text
                        )
                    }
                }
            }
            Row {
                settings.subList(6, 8).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            setting.settingType,
                            setting.text
                        )
                    }
                }
            }
            Row {
                settings.subList(8, 10).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            setting.settingType,
                            setting.text
                        )
                    }
                }
            }
            Row {
                settings.subList(10, 12).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            setting.settingType,
                            setting.text
                        )
                    }
                }
            }
            Row {
                settings.subList(12, 13).forEach { setting ->
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
}

private fun checkIsAutoTime(): Boolean {
    val contentResolver: ContentResolver = appContext.contentResolver
    val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
    return isAutoTime == 1
}

private data class Setting(val settingType: String, @StringRes val text: Int)