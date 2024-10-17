package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
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
import me.dizzykitty3.androidtoolkitty.S_POWER_USAGE_SUMMARY
import me.dizzykitty3.androidtoolkitty.S_USAGE_ACCESS
import me.dizzykitty3.androidtoolkitty.S_WIFI
import me.dizzykitty3.androidtoolkitty.S_WRITE_SETTINGS
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomHideCardSettingSwitch
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.uicomponents.Tip
import me.dizzykitty3.androidtoolkitty.utils.OSVersion

@Composable
fun SysSettingsCardEdit() {
    Card(R.string.customize_system_settings_card) {
        val haptic = LocalHapticFeedback.current
        val sp = remember { SettingsSharedPref }
        var mIsShowSetting by remember { mutableStateOf(sp.getShownState(S_WIFI)) }
        var mIsShowSetting0 by remember { mutableStateOf(sp.getShownState(S_POWER_USAGE_SUMMARY)) }
        var mIsShowSetting1 by remember { mutableStateOf(sp.getShownState(S_DISPLAY)) }
        var mIsShowSetting2 by remember { mutableStateOf(sp.getShownState(S_AUTO_ROTATE)) }
        var mIsShowSetting3 by remember { mutableStateOf(sp.getShownState(S_BLUETOOTH)) }
        var mIsShowSetting4 by remember { mutableStateOf(sp.getShownState(S_DEFAULT_APPS)) }
        var mIsShowSetting5 by remember { mutableStateOf(sp.getShownState(S_BATTERY_OPTIMIZATION)) }
        var mIsShowSetting6 by remember { mutableStateOf(sp.getShownState(S_CAPTIONING)) }
        var mIsShowSetting7 by remember { mutableStateOf(sp.getShownState(S_USAGE_ACCESS)) }
        var mIsShowSetting8 by remember { mutableStateOf(sp.getShownState(S_OVERLAY)) }
        var mIsShowSetting9 by remember { mutableStateOf(sp.getShownState(S_WRITE_SETTINGS)) }
        var mIsShowSetting10 by remember { mutableStateOf(sp.getShownState(S_ACCESSIBILITY)) }
        var mIsShowSetting11 by remember { mutableStateOf(sp.getShownState(S_LOCALE)) }
        var mIsShowSetting12 by remember { mutableStateOf(sp.getShownState(S_DATE)) }
        var mIsShowSetting13 by remember { mutableStateOf(sp.getShownState(S_DEVELOPER)) }

        Tip(R.string.sys_settings_tip)

        CustomHideCardSettingSwitch(
            text = R.string.wifi,
            card = S_WIFI,
            isChecked = mIsShowSetting
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting = newState
            sp.saveShownState(S_WIFI, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.battery_level,
            card = S_POWER_USAGE_SUMMARY,
            isChecked = mIsShowSetting0
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting0 = newState
            sp.saveShownState(S_POWER_USAGE_SUMMARY, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.display_settings,
            card = S_DISPLAY,
            isChecked = mIsShowSetting1
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting1 = newState
            sp.saveShownState(S_DISPLAY, newState)
        }
        if (OSVersion.android12()) {
            CustomHideCardSettingSwitch(
                text = R.string.auto_rotate_settings,
                card = S_AUTO_ROTATE,
                isChecked = mIsShowSetting2
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting2 = newState
                sp.saveShownState(S_AUTO_ROTATE, newState)
            }
        }
        CustomHideCardSettingSwitch(
            text = R.string.bluetooth_settings,
            card = S_BLUETOOTH,
            isChecked = mIsShowSetting3
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting3 = newState
            sp.saveShownState(S_BLUETOOTH, newState)
        }
        if (OSVersion.android7()) {
            CustomHideCardSettingSwitch(
                text = R.string.default_apps_settings,
                card = S_DEFAULT_APPS,
                isChecked = mIsShowSetting4
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting4 = newState
                sp.saveShownState(S_DEFAULT_APPS, newState)
            }
        }
        if (OSVersion.android6()) {
            CustomHideCardSettingSwitch(
                text = R.string.battery_optimization_settings,
                card = S_BATTERY_OPTIMIZATION,
                isChecked = mIsShowSetting5
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting5 = newState
                sp.saveShownState(S_BATTERY_OPTIMIZATION, newState)
            }
        }
        CustomHideCardSettingSwitch(
            text = R.string.caption_preferences,
            card = S_CAPTIONING,
            isChecked = mIsShowSetting6
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting6 = newState
            sp.saveShownState(S_CAPTIONING, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.usage_access_permission,
            card = S_USAGE_ACCESS,
            isChecked = mIsShowSetting7
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting7 = newState
            sp.saveShownState(S_USAGE_ACCESS, newState)
        }
        if (OSVersion.android6()) {
            CustomHideCardSettingSwitch(
                text = R.string.overlay_permission,
                card = S_OVERLAY,
                isChecked = mIsShowSetting8
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting8 = newState
                sp.saveShownState(S_OVERLAY, newState)
            }
        }
        if (OSVersion.android6()) {
            CustomHideCardSettingSwitch(
                text = R.string.write_permission,
                card = S_WRITE_SETTINGS,
                isChecked = mIsShowSetting9
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                mIsShowSetting9 = newState
                sp.saveShownState(S_WRITE_SETTINGS, newState)
            }
        }
        CustomHideCardSettingSwitch(
            text = R.string.accessibility_settings,
            card = S_ACCESSIBILITY,
            isChecked = mIsShowSetting10
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting10 = newState
            sp.saveShownState(S_ACCESSIBILITY, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.language_settings,
            card = S_LOCALE,
            isChecked = mIsShowSetting11
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting11 = newState
            sp.saveShownState(S_LOCALE, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.date_and_time_settings,
            card = S_DATE,
            isChecked = mIsShowSetting12
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting12 = newState
            sp.saveShownState(S_DATE, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.developer_options,
            card = S_DEVELOPER,
            isChecked = mIsShowSetting13
        ) { newState ->
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            mIsShowSetting13 = newState
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

private fun onClickChangeAllCardsButton() {
    val settingList = listOf(
        S_WIFI,
        S_POWER_USAGE_SUMMARY,
        S_DISPLAY,
        S_AUTO_ROTATE,
        S_BLUETOOTH,
        S_DEFAULT_APPS,
        S_BATTERY_OPTIMIZATION,
        S_CAPTIONING,
        S_USAGE_ACCESS,
        S_OVERLAY,
        S_WRITE_SETTINGS,
        S_ACCESSIBILITY,
        S_LOCALE,
        S_DATE,
        S_DEVELOPER
    )
    val settingsViewModel = SettingsSharedPref

    for (setting in settingList) {
        settingsViewModel.saveShownState(setting, false)
    }
}