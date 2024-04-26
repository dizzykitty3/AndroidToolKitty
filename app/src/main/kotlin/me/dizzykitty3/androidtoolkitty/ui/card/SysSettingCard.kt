package me.dizzykitty3.androidtoolkitty.ui.card

import android.content.ContentResolver
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupTitleText
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomSystemSettingsButton
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomTip
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_1
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_2
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_3
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_4
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_5
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_6
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_7
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_8
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTING_9

@Composable
fun SysSettingCard() {
    CustomCard(
        icon = Icons.Outlined.Settings,
        title = R.string.system_settings
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
            Setting(SETTING_7, R.string.open_language_settings),
            Setting(SETTING_8, R.string.open_date_and_time_settings),
            Setting(SETTING_9, R.string.open_developer_options)
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

        val isShowGroupTitle2 = settings.subList(6, settings.size).any { setting ->
            isShowSetting[setting.settingType] == true
        }

        // UI
        if (!checkIsAutoTime()) CustomTip(id = R.string.set_time_automatically_is_off_tip)

        if (isShowGroupTitle1) CustomGroupTitleText(R.string.common)

        settings.subList(0, 6).forEach { setting ->
            if (isShowSetting[setting.settingType] == true) {
                CustomSystemSettingsButton(
                    settingType = setting.settingType,
                    buttonText = setting.buttonText
                )
            }
        }

        if (isShowGroupTitle1 && isShowGroupTitle2) CustomGroupDivider()
        if (isShowGroupTitle2) CustomGroupTitleText(R.string.debugging)

        settings.subList(6, settings.size).forEach { setting ->
            if (isShowSetting[setting.settingType] == true) {
                CustomSystemSettingsButton(
                    settingType = setting.settingType,
                    buttonText = setting.buttonText
                )
            }
        }
    }
}

private fun checkIsAutoTime(): Boolean {
    val contentResolver: ContentResolver = app.applicationContext.contentResolver
    val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
    return isAutoTime == 1
}

private data class Setting(val settingType: String, val buttonText: Int)