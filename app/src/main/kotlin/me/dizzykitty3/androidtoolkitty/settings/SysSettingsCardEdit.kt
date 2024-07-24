package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
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
import androidx.compose.ui.platform.LocalView
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
import me.dizzykitty3.androidtoolkitty.S_USAGE_ACCESS
import me.dizzykitty3.androidtoolkitty.S_WRITE_SETTINGS
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomHideCardSettingSwitch
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.utils.OSVersion

@Composable
fun SysSettingsCardEdit() {
    Card(R.string.customize_system_settings_card) {
        val view = LocalView.current
        val settingsSharedPref = remember { SettingsSharedPref }
        var mIsShowSetting1 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_DISPLAY
                )
            )
        }
        var mIsShowSetting2 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_AUTO_ROTATE
                )
            )
        }
        var mIsShowSetting3 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_BLUETOOTH
                )
            )
        }
        var mIsShowSetting4 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_DEFAULT_APPS
                )
            )
        }
        var mIsShowSetting5 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_BATTERY_OPTIMIZATION
                )
            )
        }
        var mIsShowSetting6 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_CAPTIONING
                )
            )
        }
        var mIsShowSetting7 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_USAGE_ACCESS
                )
            )
        }
        var mIsShowSetting8 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_OVERLAY
                )
            )
        }
        var mIsShowSetting9 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_WRITE_SETTINGS
                )
            )
        }
        var mIsShowSetting10 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_ACCESSIBILITY
                )
            )
        }
        var mIsShowSetting11 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_LOCALE
                )
            )
        }
        var mIsShowSetting12 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_DATE
                )
            )
        }
        var mIsShowSetting13 by remember {
            mutableStateOf(
                settingsSharedPref.getCardShowedState(
                    S_DEVELOPER
                )
            )
        }

        CustomHideCardSettingSwitch(
            text = R.string.display_settings,
            card = S_DISPLAY,
            isChecked = mIsShowSetting1
        ) { newState ->
            view.hapticFeedback()
            mIsShowSetting1 = newState
            settingsSharedPref.saveCardShowedState(S_DISPLAY, newState)
        }
        if (OSVersion.a12()) {
            CustomHideCardSettingSwitch(
                text = R.string.auto_rotate_settings,
                card = S_AUTO_ROTATE,
                isChecked = mIsShowSetting2
            ) { newState ->
                view.hapticFeedback()
                mIsShowSetting2 = newState
                settingsSharedPref.saveCardShowedState(S_AUTO_ROTATE, newState)
            }
        }
        CustomHideCardSettingSwitch(
            text = R.string.bluetooth_settings,
            card = S_BLUETOOTH,
            isChecked = mIsShowSetting3
        ) { newState ->
            view.hapticFeedback()
            mIsShowSetting3 = newState
            settingsSharedPref.saveCardShowedState(S_BLUETOOTH, newState)
        }
        if (OSVersion.api24()) {
            CustomHideCardSettingSwitch(
                text = R.string.default_apps_settings,
                card = S_DEFAULT_APPS,
                isChecked = mIsShowSetting4
            ) { newState ->
                view.hapticFeedback()
                mIsShowSetting4 = newState
                settingsSharedPref.saveCardShowedState(S_DEFAULT_APPS, newState)
            }
        }
        if (OSVersion.api23()) {
            CustomHideCardSettingSwitch(
                text = R.string.battery_optimization_settings,
                card = S_BATTERY_OPTIMIZATION,
                isChecked = mIsShowSetting5
            ) { newState ->
                view.hapticFeedback()
                mIsShowSetting5 = newState
                settingsSharedPref.saveCardShowedState(S_BATTERY_OPTIMIZATION, newState)
            }
        }
        CustomHideCardSettingSwitch(
            text = R.string.caption_preferences,
            card = S_CAPTIONING,
            isChecked = mIsShowSetting6
        ) { newState ->
            view.hapticFeedback()
            mIsShowSetting6 = newState
            settingsSharedPref.saveCardShowedState(S_CAPTIONING, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.usage_access_permission,
            card = S_USAGE_ACCESS,
            isChecked = mIsShowSetting7
        ) { newState ->
            view.hapticFeedback()
            mIsShowSetting7 = newState
            settingsSharedPref.saveCardShowedState(S_USAGE_ACCESS, newState)
        }
        if (OSVersion.api23()) {
            CustomHideCardSettingSwitch(
                text = R.string.overlay_permission,
                card = S_OVERLAY,
                isChecked = mIsShowSetting8
            ) { newState ->
                view.hapticFeedback()
                mIsShowSetting8 = newState
                settingsSharedPref.saveCardShowedState(S_OVERLAY, newState)
            }
        }
        if (OSVersion.api23()) {
            CustomHideCardSettingSwitch(
                text = R.string.write_permission,
                card = S_WRITE_SETTINGS,
                isChecked = mIsShowSetting9
            ) { newState ->
                view.hapticFeedback()
                mIsShowSetting9 = newState
                settingsSharedPref.saveCardShowedState(S_WRITE_SETTINGS, newState)
            }
        }
        CustomHideCardSettingSwitch(
            text = R.string.accessibility_settings,
            card = S_ACCESSIBILITY,
            isChecked = mIsShowSetting10
        ) { newState ->
            view.hapticFeedback()
            mIsShowSetting10 = newState
            settingsSharedPref.saveCardShowedState(S_ACCESSIBILITY, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.language_settings,
            card = S_LOCALE,
            isChecked = mIsShowSetting11
        ) { newState ->
            view.hapticFeedback()
            mIsShowSetting11 = newState
            settingsSharedPref.saveCardShowedState(S_LOCALE, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.date_and_time_settings,
            card = S_DATE,
            isChecked = mIsShowSetting12
        ) { newState ->
            view.hapticFeedback()
            mIsShowSetting12 = newState
            settingsSharedPref.saveCardShowedState(S_DATE, newState)
        }
        CustomHideCardSettingSwitch(
            text = R.string.developer_options,
            card = S_DEVELOPER,
            isChecked = mIsShowSetting13
        ) { newState ->
            view.hapticFeedback()
            mIsShowSetting13 = newState
            settingsSharedPref.saveCardShowedState(S_DEVELOPER, newState)
        }

        GroupDivider()

        Button(
            {
                view.hapticFeedback()
                onClickChangeAllCardsButton(false)
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

        Button(
            {
                view.hapticFeedback()
                onClickChangeAllCardsButton(true)
                mIsShowSetting1 = true
                mIsShowSetting2 = true
                mIsShowSetting3 = true
                mIsShowSetting4 = true
                mIsShowSetting5 = true
                mIsShowSetting6 = true
                mIsShowSetting7 = true
                mIsShowSetting8 = true
                mIsShowSetting9 = true
                mIsShowSetting10 = true
                mIsShowSetting11 = true
                mIsShowSetting12 = true
                mIsShowSetting13 = true
            },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Visibility,
                contentDescription = stringResource(R.string.show_all_options),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SpacerPadding()
            Text(stringResource(R.string.show_all_options))
        }
    }
}

private fun onClickChangeAllCardsButton(isShow: Boolean) {
    val settingList = listOf(
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
        settingsViewModel.saveCardShowedState(setting, isShow)
    }
}