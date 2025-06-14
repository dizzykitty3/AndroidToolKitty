package me.dizzykitty3.androidtoolkitty.home

import android.content.ContentResolver
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_PIN_OPTIONS
import me.dizzykitty3.androidtoolkitty.SCR_SYS_SETTINGS
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
import me.dizzykitty3.androidtoolkitty.S_NOTIFICATION_LISTENER
import me.dizzykitty3.androidtoolkitty.S_NOTIFICATION_POLICY_ACCESS
import me.dizzykitty3.androidtoolkitty.S_OVERLAY
import me.dizzykitty3.androidtoolkitty.S_POWER_USAGE_SUMMARY
import me.dizzykitty3.androidtoolkitty.S_USAGE_ACCESS
import me.dizzykitty3.androidtoolkitty.S_WIFI
import me.dizzykitty3.androidtoolkitty.S_WRITE_SETTINGS
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.ItalicText
import me.dizzykitty3.androidtoolkitty.uicomponents.LabelAndValueTextRow
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.ScreenTitle
import me.dizzykitty3.androidtoolkitty.uicomponents.SystemSettingButton
import me.dizzykitty3.androidtoolkitty.uicomponents.Tip
import me.dizzykitty3.androidtoolkitty.utils.DateUtil
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.StringUtil

@Composable
fun SysSettings(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current
    Card(
        title = R.string.system,
        icon = Icons.Outlined.Settings,
        hasShowMore = true,
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_SYS_SETTINGS)
        }) {
        val settings = mutableListOf(
            Setting(S_WIFI, R.string.wifi),
            Setting(S_POWER_USAGE_SUMMARY, R.string.battery_level),
            Setting(S_DISPLAY, R.string.display_settings),
            Setting(S_AUTO_ROTATE, R.string.auto_rotate_settings),
            Setting(S_BLUETOOTH, R.string.bluetooth_settings),
            Setting(S_DEFAULT_APPS, R.string.default_apps_settings),
            Setting(S_BATTERY_OPTIMIZATION, R.string.battery_optimization_settings),
            Setting(S_CAPTIONING, R.string.caption_preferences),
            Setting(S_USAGE_ACCESS, R.string.usage_access_permission),
            Setting(S_OVERLAY, R.string.overlay_permission),
            Setting(S_WRITE_SETTINGS, R.string.write_permission),
            Setting(S_NOTIFICATION_LISTENER, R.string.device_and_app_notifications),
            Setting(S_NOTIFICATION_POLICY_ACCESS, R.string.do_not_disturb_access),
            Setting(S_ACCESSIBILITY, R.string.accessibility_settings),
            Setting(S_LOCALE, R.string.language_settings),
            Setting(S_DATE, R.string.date_and_time_settings),
            Setting(S_DEVELOPER, R.string.developer_options)
        )
        if (!OSVersion.android12()) {
            settings.remove(Setting(S_AUTO_ROTATE, R.string.auto_rotate_settings))
        }
        if (!OSVersion.android7()) {
            settings.remove(Setting(S_DEFAULT_APPS, R.string.default_apps_settings))
        }
        if (!OSVersion.android6()) {
            settings.remove(Setting(S_BATTERY_OPTIMIZATION, R.string.battery_optimization_settings))
            settings.remove(Setting(S_OVERLAY, R.string.overlay_permission))
            settings.remove(Setting(S_WRITE_SETTINGS, R.string.write_permission))
        }
        val shownSettings = settings.filter { setting ->
            SettingsSharedPref.getShownState(setting.settingType)
        }

        if (!checkIsAutoTime()) Tip(R.string.set_time_automatically_is_off_tip)

        val count = shownSettings.count()
        if (count == 0) {
            Text(buildAnnotatedString { ItalicText(R.string.no_options_enabled) })
        } else if (count <= 2) {
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                shownSettings.subList(0, count).forEach { setting ->
                    SystemSettingButton(
                        setting.settingType,
                        setting.text
                    )
                }
            }
        } else {
            Column(Modifier.horizontalScroll(rememberScrollState())) {
                Row {
                    shownSettings.subList(0, 2).forEach { setting ->
                        SystemSettingButton(
                            setting.settingType,
                            setting.text
                        )
                    }
                }
                Row {
                    shownSettings.subList(2, minOf(4, count)).forEach { setting ->
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

@Composable
fun SysSettingsScreen(navController: NavHostController) {
    Screen {
        val haptic = LocalHapticFeedback.current

        ScreenTitle(R.string.system)

        val settings = mutableListOf(
            Setting(S_WIFI, R.string.wifi),
            Setting(S_POWER_USAGE_SUMMARY, R.string.battery_level),
            Setting(S_DISPLAY, R.string.display_settings),
            Setting(S_AUTO_ROTATE, R.string.auto_rotate_settings),
            Setting(S_BLUETOOTH, R.string.bluetooth_settings),
            Setting(S_DEFAULT_APPS, R.string.default_apps_settings),
            Setting(S_BATTERY_OPTIMIZATION, R.string.battery_optimization_settings),
            Setting(S_CAPTIONING, R.string.caption_preferences),
            Setting(S_USAGE_ACCESS, R.string.usage_access_permission),
            Setting(S_OVERLAY, R.string.overlay_permission),
            Setting(S_WRITE_SETTINGS, R.string.write_permission),
            Setting(S_NOTIFICATION_LISTENER, R.string.device_and_app_notifications),
            Setting(S_NOTIFICATION_POLICY_ACCESS, R.string.do_not_disturb_access),
            Setting(S_ACCESSIBILITY, R.string.accessibility_settings),
            Setting(S_LOCALE, R.string.language_settings),
            Setting(S_DATE, R.string.date_and_time_settings),
            Setting(S_DEVELOPER, R.string.developer_options)
        )
        if (!OSVersion.android12()) {
            settings.remove(Setting(S_AUTO_ROTATE, R.string.auto_rotate_settings))
        }
        if (!OSVersion.android7()) {
            settings.remove(Setting(S_DEFAULT_APPS, R.string.default_apps_settings))
        }
        if (!OSVersion.android6()) {
            settings.remove(
                Setting(
                    S_BATTERY_OPTIMIZATION,
                    R.string.battery_optimization_settings
                )
            )
            settings.remove(Setting(S_OVERLAY, R.string.overlay_permission))
            settings.remove(Setting(S_WRITE_SETTINGS, R.string.write_permission))
        }

        val i1 = settings.indexOf(Setting(S_CAPTIONING, R.string.caption_preferences)) + 1
        val i2 = settings.indexOf(Setting(S_ACCESSIBILITY, R.string.accessibility_settings)) + 1
        val i3 = settings.count()

        Card(R.string.device_info) {
            Column(Modifier.fillMaxWidth()) {
                LabelAndValueTextRow("manufacturer", StringUtil.manufacturer)
                LabelAndValueTextRow("model", StringUtil.model)
                LabelAndValueTextRow("device", StringUtil.device)
                LabelAndValueTextRow("os_ver", StringUtil.osVer)
                LabelAndValueTextRow("locale", StringUtil.sysLocale)
                LabelAndValueTextRow("time_zone", DateUtil.sysTimeZone)
            }
        }
        Card(R.string.general) {
            settings.subList(0, i1).forEach { setting ->
                SystemSettingButton(
                    setting.settingType,
                    setting.text
                )
            }
        }
        Card(R.string.permission) {
            settings.subList(i1, i2).forEach { setting ->
                SystemSettingButton(
                    setting.settingType,
                    setting.text
                )
            }
        }
        Card(R.string.debugging) {
            settings.subList(i2, i3).forEach { setting ->
                SystemSettingButton(
                    setting.settingType,
                    setting.text
                )
            }
        }

        // edit
        Button(onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_PIN_OPTIONS)
        }) { Text(stringResource(R.string.customize_system_settings_card)) }
    }
}

private fun checkIsAutoTime(): Boolean {
    val contentResolver: ContentResolver = appContext.contentResolver
    val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
    return isAutoTime == 1
}

private data class Setting(val settingType: String, @StringRes val text: Int)