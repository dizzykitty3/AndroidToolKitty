package me.dizzykitty3.androidtoolkitty.ui.home

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
import me.dizzykitty3.androidtoolkitty.SETTING_AUTO_ROTATE
import me.dizzykitty3.androidtoolkitty.SETTING_BATTERY_OPTIMIZATION
import me.dizzykitty3.androidtoolkitty.SETTING_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.SETTING_CAPTIONING
import me.dizzykitty3.androidtoolkitty.SETTING_DATE
import me.dizzykitty3.androidtoolkitty.SETTING_DEFAULT_APPS
import me.dizzykitty3.androidtoolkitty.SETTING_DEVELOPER
import me.dizzykitty3.androidtoolkitty.SETTING_DISPLAY
import me.dizzykitty3.androidtoolkitty.SETTING_LOCALE
import me.dizzykitty3.androidtoolkitty.SETTING_OVERLAY
import me.dizzykitty3.androidtoolkitty.SETTING_USAGE_ACCESS
import me.dizzykitty3.androidtoolkitty.SETTING_WRITE_SETTINGS
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui_components.Card
import me.dizzykitty3.androidtoolkitty.ui_components.CustomTip
import me.dizzykitty3.androidtoolkitty.ui_components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui_components.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui_components.SystemSettingButton
import me.dizzykitty3.androidtoolkitty.utils.OSVersion

@Composable
fun SysSettings() {
    Card(
        icon = Icons.Outlined.Settings,
        title = R.string.system_settings
    ) {
        val settingsSharedPref = remember { SettingsSharedPref }

        val settings = listOf(
            Setting(SETTING_DISPLAY, R.string.open_display_settings),
            Setting(SETTING_AUTO_ROTATE, R.string.open_auto_rotate_settings),
            Setting(SETTING_BLUETOOTH, R.string.open_bluetooth_settings),
            Setting(SETTING_DEFAULT_APPS, R.string.open_default_apps_settings),
            Setting(SETTING_BATTERY_OPTIMIZATION, R.string.open_battery_optimization_settings),
            Setting(SETTING_CAPTIONING, R.string.open_caption_preferences),
            Setting(SETTING_USAGE_ACCESS, R.string.open_usage_access_permission),
            Setting(SETTING_OVERLAY, R.string.open_overlay_permission),
            Setting(SETTING_WRITE_SETTINGS, R.string.open_write_permission),
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

        val isShowGroupTitle1 = settings.subList(0, 6).any { setting ->
            isShowSetting[setting.settingType] == true
        }

        val isShowGroupTitle2 = settings.subList(6, 9).any { setting ->
            isShowSetting[setting.settingType] == true
        }

        val isShowGroupTitle3 = settings.subList(9, 12).any { setting ->
            isShowSetting[setting.settingType] == true
        }

        val uiTesting = settingsSharedPref.uiTesting

        if (!checkIsAutoTime() || uiTesting) CustomTip(message = R.string.set_time_automatically_is_off_tip)

        if (isShowGroupTitle1) GroupTitle(R.string.common)

        Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Row {
                if (isShowSetting[SETTING_DISPLAY] == true) {
                    SystemSettingButton(
                        settingType = SETTING_DISPLAY,
                        text = R.string.open_display_settings
                    )
                }

                if ((isShowSetting[SETTING_AUTO_ROTATE] == true) && OSVersion.android12()) {
                    SystemSettingButton(
                        settingType = SETTING_AUTO_ROTATE,
                        text = R.string.open_auto_rotate_settings
                    )
                }
            }

            Row {
                settings.subList(2, 4).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            settingType = setting.settingType,
                            text = setting.text
                        )
                    }
                }
            }

            Row {
                settings.subList(4, 6).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            settingType = setting.settingType,
                            text = setting.text
                        )
                    }
                }
            }
        }

        if (isShowGroupTitle1 && isShowGroupTitle2) GroupDivider()
        if (isShowGroupTitle2) GroupTitle(R.string.permission)

        Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Row {
                settings.subList(6, 8).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            settingType = setting.settingType,
                            text = setting.text
                        )
                    }
                }
            }

            Row {
                if (isShowSetting[SETTING_WRITE_SETTINGS] == true) {
                    SystemSettingButton(
                        settingType = SETTING_WRITE_SETTINGS,
                        text = R.string.open_write_permission
                    )
                }
            }
        }

        if ((isShowGroupTitle1 || isShowGroupTitle2) && isShowGroupTitle3) GroupDivider()
        if (isShowGroupTitle3) GroupTitle(R.string.debugging)

        Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Row {
                settings.subList(9, 11).forEach { setting ->
                    if (isShowSetting[setting.settingType] == true) {
                        SystemSettingButton(
                            settingType = setting.settingType,
                            text = setting.text
                        )
                    }
                }
            }

            Row {
                if (isShowSetting[SETTING_DEVELOPER] == true) {
                    SystemSettingButton(
                        settingType = SETTING_DEVELOPER,
                        text = R.string.open_developer_options
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