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
import me.dizzykitty3.androidtoolkitty.*
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.*
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
            Setting(S_ABOUT_PHONE, R.string.about_phone),
            Setting(S_SEARCH_SETTINGS, R.string.search_settings),
            // General
            Setting(S_WIFI, R.string.wifi),
            Setting(S_BATTERY_LEVEL, R.string.battery_level),
            Setting(S_DISPLAY, R.string.display_settings),
            Setting(S_AUTO_ROTATE, R.string.auto_rotate_settings),
            Setting(S_SOUND, R.string.sound),
            Setting(S_BLUETOOTH, R.string.bluetooth_settings),
            Setting(S_DEFAULT_APPS, R.string.default_apps_settings),
            Setting(S_KEYBOARD, R.string.keyboard),
            Setting(S_BATTERY_OPTIMIZATION, R.string.battery_optimization_settings),
            Setting(S_CAPTION, R.string.caption_preferences),
            Setting(S_ACCOUNTS, R.string.accounts),
            Setting(S_VPN, R.string.vpn),
            Setting(S_NFC, R.string.nfc),
            // Permission
            Setting(S_APP_NOTIFICATIONS, R.string.app_notifications),
            Setting(S_UNKNOWN_APPS, R.string.install_unknown_apps),
            Setting(S_MEDIA_MANAGEMENT, R.string.media_management),
            Setting(S_USAGE_ACCESS, R.string.usage_access_permission),
            Setting(S_OVERLAY, R.string.overlay_permission),
            Setting(S_MODIFY_SYSTEM, R.string.modify_system),
            Setting(S_NOTIFICATION_LISTENER, R.string.device_and_app_notifications),
            Setting(S_DND_ACCESS, R.string.do_not_disturb_access),
            Setting(S_ALARMS, R.string.alarms_n_reminders),
            Setting(S_ACCESSIBILITY, R.string.accessibility_settings),
            // Debugging
            Setting(S_LOCALE, R.string.language_settings),
            Setting(S_DATE, R.string.date_and_time_settings),
            Setting(S_DEVELOPER, R.string.developer_options)
        )
        if (!OSVersion.android13()) {
            settings.remove(Setting(S_APP_NOTIFICATIONS, R.string.app_notifications))
        }
        if (!OSVersion.android12()) {
            settings.remove(Setting(S_AUTO_ROTATE, R.string.auto_rotate_settings))
            settings.remove(Setting(S_ALARMS, R.string.alarms_n_reminders))
            settings.remove(Setting(S_MEDIA_MANAGEMENT, R.string.media_management))
        }
        if (!OSVersion.android10()) {
            settings.remove(Setting(S_SEARCH_SETTINGS, R.string.search_settings))
        }
        if (!OSVersion.android8()) {
            settings.remove(Setting(S_UNKNOWN_APPS, R.string.install_unknown_apps))
        }
        if (!OSVersion.android7()) {
            settings.remove(Setting(S_DEFAULT_APPS, R.string.default_apps_settings))
            settings.remove(Setting(S_VPN, R.string.vpn))
        }
        if (!OSVersion.android6()) {
            settings.remove(Setting(S_BATTERY_OPTIMIZATION, R.string.battery_optimization_settings))
            settings.remove(Setting(S_OVERLAY, R.string.overlay_permission))
            settings.remove(Setting(S_MODIFY_SYSTEM, R.string.modify_system))
        }
        val shownSettings = settings.filter { setting ->
            SettingsSharedPref.getShownState(setting.settingType)
        }

        if (!checkIsAutoTime()) Tip(R.string.auto_set_time_is_off_tip)

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
    Screen(navController) {
        val haptic = LocalHapticFeedback.current

        ScreenTitle(R.string.system)

        val settings = mutableListOf(
            Setting(S_ABOUT_PHONE, R.string.about_phone),
            Setting(S_SEARCH_SETTINGS, R.string.search_settings),
            // General
            Setting(S_WIFI, R.string.wifi),
            Setting(S_BATTERY_LEVEL, R.string.battery_level),
            Setting(S_DISPLAY, R.string.display_settings),
            Setting(S_AUTO_ROTATE, R.string.auto_rotate_settings),
            Setting(S_SOUND, R.string.sound),
            Setting(S_BLUETOOTH, R.string.bluetooth_settings),
            Setting(S_DEFAULT_APPS, R.string.default_apps_settings),
            Setting(S_KEYBOARD, R.string.keyboard),
            Setting(S_BATTERY_OPTIMIZATION, R.string.battery_optimization_settings),
            Setting(S_CAPTION, R.string.caption_preferences),
            Setting(S_ACCOUNTS, R.string.accounts),
            Setting(S_VPN, R.string.vpn),
            Setting(S_NFC, R.string.nfc),
            // Permission
            Setting(S_APP_NOTIFICATIONS, R.string.app_notifications),
            Setting(S_UNKNOWN_APPS, R.string.install_unknown_apps),
            Setting(S_MEDIA_MANAGEMENT, R.string.media_management),
            Setting(S_USAGE_ACCESS, R.string.usage_access_permission),
            Setting(S_OVERLAY, R.string.overlay_permission),
            Setting(S_MODIFY_SYSTEM, R.string.modify_system),
            Setting(S_NOTIFICATION_LISTENER, R.string.device_and_app_notifications),
            Setting(S_DND_ACCESS, R.string.do_not_disturb_access),
            Setting(S_ALARMS, R.string.alarms_n_reminders),
            Setting(S_ACCESSIBILITY, R.string.accessibility_settings),
            // Debugging
            Setting(S_LOCALE, R.string.language_settings),
            Setting(S_DATE, R.string.date_and_time_settings),
            Setting(S_DEVELOPER, R.string.developer_options)
        )
        if (!OSVersion.android13()) {
            settings.remove(Setting(S_APP_NOTIFICATIONS, R.string.app_notifications))
        }
        if (!OSVersion.android12()) {
            settings.remove(Setting(S_AUTO_ROTATE, R.string.auto_rotate_settings))
            settings.remove(Setting(S_ALARMS, R.string.alarms_n_reminders))
            settings.remove(Setting(S_MEDIA_MANAGEMENT, R.string.media_management))
        }
        if (!OSVersion.android10()) {
            settings.remove(Setting(S_SEARCH_SETTINGS, R.string.search_settings))
        }
        if (!OSVersion.android8()) {
            settings.remove(Setting(S_UNKNOWN_APPS, R.string.install_unknown_apps))
        }
        if (!OSVersion.android7()) {
            settings.remove(Setting(S_DEFAULT_APPS, R.string.default_apps_settings))
            settings.remove(Setting(S_VPN, R.string.vpn))
        }
        if (!OSVersion.android6()) {
            settings.remove(Setting(S_BATTERY_OPTIMIZATION, R.string.battery_optimization_settings))
            settings.remove(Setting(S_OVERLAY, R.string.overlay_permission))
            settings.remove(Setting(S_MODIFY_SYSTEM, R.string.modify_system))
        }

        val i1 = settings.indexOf(Setting(S_NFC, R.string.nfc)) + 1
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

private data class Setting(val settingType: String, @param:StringRes val text: Int)