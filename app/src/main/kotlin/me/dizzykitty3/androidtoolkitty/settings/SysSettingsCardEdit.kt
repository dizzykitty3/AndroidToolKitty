package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.*
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.*
import me.dizzykitty3.androidtoolkitty.utils.OSVersion

@Composable
fun SysSettingsCardEditScreen(navController: NavHostController) {
    Screen(navController = navController) {
        Card(R.string.customize_system_settings_card) {
            val haptic = LocalHapticFeedback.current
            val sp = remember { SettingsSharedPref }
            var mIsShowSetting by remember { mutableStateOf(sp.getShownState(S_ABOUT_PHONE)) }
            var mIsShowSetting0 by remember { mutableStateOf(sp.getShownState(S_SEARCH_SETTINGS)) }
            var mIsShowSetting1 by remember { mutableStateOf(sp.getShownState(S_WIFI)) }
            var mIsShowSetting2 by remember { mutableStateOf(sp.getShownState(S_BATTERY)) }
            var mIsShowSetting3 by remember { mutableStateOf(sp.getShownState(S_DISPLAY)) }
            var mIsShowSetting4 by remember { mutableStateOf(sp.getShownState(S_AUTO_ROTATE)) }
            var mIsShowSetting5 by remember { mutableStateOf(sp.getShownState(S_SOUND)) }
            var mIsShowSetting6 by remember { mutableStateOf(sp.getShownState(S_BLUETOOTH)) }
            var mIsShowSetting7 by remember { mutableStateOf(sp.getShownState(S_DEFAULT_APPS)) }
            var mIsShowSetting8 by remember { mutableStateOf(sp.getShownState(S_KEYBOARD)) }
            var mIsShowSetting9 by remember { mutableStateOf(sp.getShownState(S_BATTERY_OPTIMIZATION)) }
            var mIsShowSetting10 by remember { mutableStateOf(sp.getShownState(S_CAPTION)) }
            var mIsShowSetting11 by remember { mutableStateOf(sp.getShownState(S_ACCOUNTS)) }
            var mIsShowSetting12 by remember { mutableStateOf(sp.getShownState(S_VPN)) }
            var mIsShowSetting13 by remember { mutableStateOf(sp.getShownState(S_NFC)) }
            var mIsShowSetting14 by remember { mutableStateOf(sp.getShownState(S_APP_NOTIFICATIONS)) }
            var mIsShowSetting15 by remember { mutableStateOf(sp.getShownState(S_UNKNOWN_APPS)) }
            var mIsShowSetting16 by remember { mutableStateOf(sp.getShownState(S_MEDIA_MANAGEMENT)) }
            var mIsShowSetting17 by remember { mutableStateOf(sp.getShownState(S_USAGE_ACCESS)) }
            var mIsShowSetting18 by remember { mutableStateOf(sp.getShownState(S_OVERLAY)) }
            var mIsShowSetting19 by remember { mutableStateOf(sp.getShownState(S_MODIFY_SYSTEM)) }
            var mIsShowSetting20 by remember { mutableStateOf(sp.getShownState(S_APP_NOTIFICATIONS)) }
            var mIsShowSetting21 by remember { mutableStateOf(sp.getShownState(S_DND_ACCESS)) }
            var mIsShowSetting22 by remember { mutableStateOf(sp.getShownState(S_ALARMS)) }
            var mIsShowSetting23 by remember { mutableStateOf(sp.getShownState(S_ACCESSIBILITY)) }
            var mIsShowSetting24 by remember { mutableStateOf(sp.getShownState(S_LOCALE)) }
            var mIsShowSetting25 by remember { mutableStateOf(sp.getShownState(S_DATE)) }
            var mIsShowSetting26 by remember { mutableStateOf(sp.getShownState(S_DEVELOPER)) }

            Tip(R.string.sys_settings_tip)

            CustomHideCardSettingSwitch(
                text = R.string.about_phone,
                card = S_ABOUT_PHONE,
                isChecked = mIsShowSetting
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting = newState
                sp.saveShownState(S_ABOUT_PHONE, newState)
            }
            if (OSVersion.android10()) {
                CustomHideCardSettingSwitch(
                    text = R.string.search_settings,
                    card = S_SEARCH_SETTINGS,
                    isChecked = mIsShowSetting0
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting0 = newState
                    sp.saveShownState(S_SEARCH_SETTINGS, newState)
                }
            }
            CustomHideCardSettingSwitch(
                text = R.string.wifi,
                card = S_WIFI,
                isChecked = mIsShowSetting1
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting1 = newState
                sp.saveShownState(S_WIFI, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.battery,
                card = S_BATTERY,
                isChecked = mIsShowSetting2
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting2 = newState
                sp.saveShownState(S_BATTERY, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.display_settings,
                card = S_DISPLAY,
                isChecked = mIsShowSetting3
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting3 = newState
                sp.saveShownState(S_DISPLAY, newState)
            }
            if (OSVersion.android12()) {
                CustomHideCardSettingSwitch(
                    text = R.string.auto_rotate_settings,
                    card = S_AUTO_ROTATE,
                    isChecked = mIsShowSetting4
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting4 = newState
                    sp.saveShownState(S_AUTO_ROTATE, newState)
                }
            }
            CustomHideCardSettingSwitch(
                text = R.string.sound,
                card = S_SOUND,
                isChecked = mIsShowSetting5
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting5 = newState
                sp.saveShownState(S_SOUND, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.bluetooth_settings,
                card = S_BLUETOOTH,
                isChecked = mIsShowSetting6
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting6 = newState
                sp.saveShownState(S_BLUETOOTH, newState)
            }
            if (OSVersion.android7()) {
                CustomHideCardSettingSwitch(
                    text = R.string.default_apps_settings,
                    card = S_DEFAULT_APPS,
                    isChecked = mIsShowSetting7
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting7 = newState
                    sp.saveShownState(S_DEFAULT_APPS, newState)
                }
            }
            CustomHideCardSettingSwitch(
                text = R.string.keyboard,
                card = S_KEYBOARD,
                isChecked = mIsShowSetting8
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting8 = newState
                sp.saveShownState(S_KEYBOARD, newState)
            }
            if (OSVersion.android6()) {
                CustomHideCardSettingSwitch(
                    text = R.string.battery_optimization_settings,
                    card = S_BATTERY_OPTIMIZATION,
                    isChecked = mIsShowSetting9
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting9 = newState
                    sp.saveShownState(S_BATTERY_OPTIMIZATION, newState)
                }
            }
            CustomHideCardSettingSwitch(
                text = R.string.caption_preferences,
                card = S_CAPTION,
                isChecked = mIsShowSetting10
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting10 = newState
                sp.saveShownState(S_CAPTION, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.accounts,
                card = S_ACCOUNTS,
                isChecked = mIsShowSetting11
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting11 = newState
                sp.saveShownState(S_ACCOUNTS, newState)
            }
            if (OSVersion.android7()) {
                CustomHideCardSettingSwitch(
                    text = R.string.vpn,
                    card = S_VPN,
                    isChecked = mIsShowSetting12
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting12 = newState
                    sp.saveShownState(S_VPN, newState)
                }
            }
            CustomHideCardSettingSwitch(
                text = R.string.nfc,
                card = S_NFC,
                isChecked = mIsShowSetting13
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting13 = newState
                sp.saveShownState(S_NFC, newState)
            }
            if (OSVersion.android13()) {
                CustomHideCardSettingSwitch(
                    text = R.string.app_notifications,
                    card = S_APP_NOTIFICATIONS,
                    isChecked = mIsShowSetting14
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting14 = newState
                    sp.saveShownState(S_APP_NOTIFICATIONS, newState)
                }
            }
            if (OSVersion.android8()) {
                CustomHideCardSettingSwitch(
                    text = R.string.install_unknown_apps,
                    card = S_UNKNOWN_APPS,
                    isChecked = mIsShowSetting15
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting15 = newState
                    sp.saveShownState(S_UNKNOWN_APPS, newState)
                }
            }
            if (OSVersion.android12()) {
                CustomHideCardSettingSwitch(
                    text = R.string.media_management,
                    card = S_MEDIA_MANAGEMENT,
                    isChecked = mIsShowSetting16
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting16 = newState
                    sp.saveShownState(S_MEDIA_MANAGEMENT, newState)
                }
            }
            CustomHideCardSettingSwitch(
                text = R.string.usage_access_permission,
                card = S_USAGE_ACCESS,
                isChecked = mIsShowSetting17
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting17 = newState
                sp.saveShownState(S_USAGE_ACCESS, newState)
            }
            if (OSVersion.android6()) {
                CustomHideCardSettingSwitch(
                    text = R.string.overlay_permission,
                    card = S_OVERLAY,
                    isChecked = mIsShowSetting18
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting18 = newState
                    sp.saveShownState(S_OVERLAY, newState)
                }
            }
            if (OSVersion.android6()) {
                CustomHideCardSettingSwitch(
                    text = R.string.modify_system,
                    card = S_MODIFY_SYSTEM,
                    isChecked = mIsShowSetting19
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting19 = newState
                    sp.saveShownState(S_MODIFY_SYSTEM, newState)
                }
            }
            if (OSVersion.android5Point1()) {
                CustomHideCardSettingSwitch(
                    text = R.string.app_notifications,
                    card = S_APP_NOTIFICATIONS,
                    isChecked = mIsShowSetting20
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting20 = newState
                    sp.saveShownState(S_APP_NOTIFICATIONS, newState)
                }
            }
            if (OSVersion.android6()) {
                CustomHideCardSettingSwitch(
                    text = R.string.do_not_disturb_access,
                    card = S_DND_ACCESS,
                    isChecked = mIsShowSetting21
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting21 = newState
                    sp.saveShownState(S_DND_ACCESS, newState)
                }
            }
            if (OSVersion.android12()) {
                CustomHideCardSettingSwitch(
                    text = R.string.alarms_n_reminders,
                    card = S_ALARMS,
                    isChecked = mIsShowSetting22
                ) { newState ->
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    mIsShowSetting22 = newState
                    sp.saveShownState(S_ALARMS, newState)
                }
            }
            CustomHideCardSettingSwitch(
                text = R.string.accessibility_settings,
                card = S_ACCESSIBILITY,
                isChecked = mIsShowSetting23
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting23 = newState
                sp.saveShownState(S_ACCESSIBILITY, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.language_settings,
                card = S_LOCALE,
                isChecked = mIsShowSetting24
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting24 = newState
                sp.saveShownState(S_LOCALE, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.date_and_time_settings,
                card = S_DATE,
                isChecked = mIsShowSetting25
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting25 = newState
                sp.saveShownState(S_DATE, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.developer_options,
                card = S_DEVELOPER,
                isChecked = mIsShowSetting26
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting26 = newState
                sp.saveShownState(S_DEVELOPER, newState)
            }

            SpacerPadding()

            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClickChangeAllCardsButton()
                    mIsShowSetting = false
                    mIsShowSetting0 = false
                    mIsShowSetting1 = false
                    mIsShowSetting2 = false
                    mIsShowSetting3 = false
                    mIsShowSetting4 = false
                    mIsShowSetting5 = false
                    mIsShowSetting6 = false
                    mIsShowSetting7 = false
                    mIsShowSetting8 = false
                    mIsShowSetting9 = false
                    mIsShowSetting10 = false
                    mIsShowSetting11 = false
                    mIsShowSetting12 = false
                    mIsShowSetting13 = false
                    mIsShowSetting14 = false
                    mIsShowSetting15 = false
                    mIsShowSetting16 = false
                    mIsShowSetting17 = false
                    mIsShowSetting18 = false
                    mIsShowSetting19 = false
                    mIsShowSetting20 = false
                    mIsShowSetting21 = false
                    mIsShowSetting22 = false
                    mIsShowSetting23 = false
                    mIsShowSetting24 = false
                    mIsShowSetting25 = false
                    mIsShowSetting26 = false
                },
                elevation = ButtonDefaults.buttonElevation(1.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.VisibilityOff,
                    contentDescription = stringResource(R.string.hide_all_options),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                SpacerPadding()
                Text(stringResource(R.string.hide_all_options))
            }
        }
    }
}

private fun onClickChangeAllCardsButton() {
    val settingList = listOf(
        S_ABOUT_PHONE, S_SEARCH_SETTINGS, S_WIFI, S_BATTERY, S_DISPLAY, S_AUTO_ROTATE, S_SOUND, S_BLUETOOTH,
        S_DEFAULT_APPS, S_KEYBOARD, S_BATTERY_OPTIMIZATION, S_CAPTION, S_ACCOUNTS, S_VPN, S_NFC, S_APP_NOTIFICATIONS,
        S_UNKNOWN_APPS, S_MEDIA_MANAGEMENT, S_USAGE_ACCESS, S_OVERLAY, S_MODIFY_SYSTEM, S_NOTIFICATION_LISTENER,
        S_DND_ACCESS, S_ALARMS, S_ACCESSIBILITY, S_LOCALE, S_DATE, S_DEVELOPER
    )
    val settingsViewModel = SettingsSharedPref

    for (setting in settingList) {
        settingsViewModel.saveShownState(setting, false)
    }
}