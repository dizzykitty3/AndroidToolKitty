package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

import android.content.ContentResolver
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
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
import me.dizzykitty3.androidtoolkitty.foundation.util.OSVersion
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.ui.component.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.component.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui.component.SystemSettingButton

@Composable
fun SysSettingCard() {
    CustomCard(
        icon = Icons.Outlined.Settings,
        titleRes = R.string.system_settings
    ) {
        // Variables
        val settingsSharedPref = remember { SettingsSharedPref }

        val settings = listOf(
            Setting(SETTING_1, R.string.open_display_settings),
            Setting(SETTING_2, R.string.open_auto_rotate_settings),
            Setting(SETTING_3, R.string.open_bluetooth_settings),
            Setting(SETTING_4, R.string.open_default_apps_settings),
            Setting(SETTING_5, R.string.open_battery_optimization_settings),
            Setting(SETTING_6, R.string.open_caption_preferences),
            Setting(SETTING_7, R.string.open_usage_access_permission),
            Setting(SETTING_8, R.string.open_overlay_permission),
            Setting(SETTING_9, R.string.open_write_permission),
            Setting(SETTING_10, R.string.open_language_settings),
            Setting(SETTING_11, R.string.open_date_and_time_settings),
            Setting(SETTING_12, R.string.open_developer_options)
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

        // UI
        if (!checkIsAutoTime() || uiTesting) CustomTip(id = R.string.set_time_automatically_is_off_tip)

        if (isShowGroupTitle1) GroupTitle(R.string.common)

        if (isShowSetting[SETTING_1] == true) {
            SystemSettingButton(
                settingType = SETTING_1,
                textRes = R.string.open_display_settings
            )
        }

        if (isShowSetting[SETTING_2] == true && OSVersion.android12()) {
            SystemSettingButton(
                settingType = SETTING_2,
                textRes = R.string.open_auto_rotate_settings
            )
        }

        settings.subList(2, 6).forEach { setting ->
            if (isShowSetting[setting.settingType] == true) {
                SystemSettingButton(
                    settingType = setting.settingType,
                    textRes = setting.textRes
                )
            }
        }

        if (isShowGroupTitle1 && isShowGroupTitle2) GroupDivider()
        if (isShowGroupTitle2) GroupTitle(R.string.permission)

        settings.subList(6, 9).forEach { setting ->
            if (isShowSetting[setting.settingType] == true) {
                SystemSettingButton(
                    settingType = setting.settingType,
                    textRes = setting.textRes
                )
            }
        }

        if ((isShowGroupTitle1 || isShowGroupTitle2) && isShowGroupTitle3) GroupDivider()
        if (isShowGroupTitle3) GroupTitle(R.string.debugging)

        settings.subList(9, 12).forEach { setting ->
            if (isShowSetting[setting.settingType] == true) {
                SystemSettingButton(
                    settingType = setting.settingType,
                    textRes = setting.textRes
                )
            }
        }
    }
}

private fun checkIsAutoTime(): Boolean {
    val contentResolver: ContentResolver = appContext.contentResolver
    val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
    return isAutoTime == 1
}

private data class Setting(val settingType: String, @StringRes val textRes: Int)