package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomHideCardSettingSwitch
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomSpacerPadding
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
import me.dizzykitty3.androidtoolkitty.foundation.util.OsVersion
import me.dizzykitty3.androidtoolkitty.foundation.util.SnackbarUtil

/**
 * @see SysSettingCard
 */
// TODO refactor this bullshit
@Composable
fun EditSysSettingCard() {
    CustomCard(
        title = R.string.customize_system_settings_card
    ) {
        val settingsSharedPref = remember { SettingsSharedPref }

        val isShowSetting1 = settingsSharedPref.getCardShowedState(SETTING_1)
        val isShowSetting2 = settingsSharedPref.getCardShowedState(SETTING_2)
        val isShowSetting3 = settingsSharedPref.getCardShowedState(SETTING_3)
        val isShowSetting4 = settingsSharedPref.getCardShowedState(SETTING_4)
        val isShowSetting5 = settingsSharedPref.getCardShowedState(SETTING_5)
        val isShowSetting6 = settingsSharedPref.getCardShowedState(SETTING_6)
        val isShowSetting7 = settingsSharedPref.getCardShowedState(SETTING_7)
        val isShowSetting8 = settingsSharedPref.getCardShowedState(SETTING_8)
        val isShowSetting9 = settingsSharedPref.getCardShowedState(SETTING_9)
        val isShowSetting10 = settingsSharedPref.getCardShowedState(SETTING_10)
        val isShowSetting11 = settingsSharedPref.getCardShowedState(SETTING_11)
        val isShowSetting12 = settingsSharedPref.getCardShowedState(SETTING_12)
        var mIsShowSetting1 by remember { mutableStateOf(isShowSetting1) }
        var mIsShowSetting2 by remember { mutableStateOf(isShowSetting2) }
        var mIsShowSetting3 by remember { mutableStateOf(isShowSetting3) }
        var mIsShowSetting4 by remember { mutableStateOf(isShowSetting4) }
        var mIsShowSetting5 by remember { mutableStateOf(isShowSetting5) }
        var mIsShowSetting6 by remember { mutableStateOf(isShowSetting6) }
        var mIsShowSetting7 by remember { mutableStateOf(isShowSetting7) }
        var mIsShowSetting8 by remember { mutableStateOf(isShowSetting8) }
        var mIsShowSetting9 by remember { mutableStateOf(isShowSetting9) }
        var mIsShowSetting10 by remember { mutableStateOf(isShowSetting10) }
        var mIsShowSetting11 by remember { mutableStateOf(isShowSetting11) }
        var mIsShowSetting12 by remember { mutableStateOf(isShowSetting12) }

        CustomHideCardSettingSwitch(
            id = R.string.open_display_settings,
            cardId = SETTING_1,
            isChecked = mIsShowSetting1
        ) { newState ->
            mIsShowSetting1 = newState
            settingsSharedPref.saveCardShowedState(SETTING_1, newState)
        }
        if (OsVersion.android12()) {
            CustomHideCardSettingSwitch(
                id = R.string.open_auto_rotate_settings,
                cardId = SETTING_2,
                isChecked = mIsShowSetting2
            ) { newState ->
                mIsShowSetting2 = newState
                settingsSharedPref.saveCardShowedState(SETTING_2, newState)
            }
        }
        CustomHideCardSettingSwitch(
            id = R.string.open_bluetooth_settings,
            cardId = SETTING_3,
            isChecked = mIsShowSetting3
        ) { newState ->
            mIsShowSetting3 = newState
            settingsSharedPref.saveCardShowedState(SETTING_3, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.open_default_apps_settings,
            cardId = SETTING_4,
            isChecked = mIsShowSetting4
        ) { newState ->
            mIsShowSetting4 = newState
            settingsSharedPref.saveCardShowedState(SETTING_4, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.open_battery_optimization_settings,
            cardId = SETTING_5,
            isChecked = mIsShowSetting5
        ) { newState ->
            mIsShowSetting5 = newState
            settingsSharedPref.saveCardShowedState(SETTING_5, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.open_caption_preferences,
            cardId = SETTING_6,
            isChecked = mIsShowSetting6
        ) { newState ->
            mIsShowSetting6 = newState
            settingsSharedPref.saveCardShowedState(SETTING_6, newState)
        }

        CustomGroupDivider()

        CustomHideCardSettingSwitch(
            id = R.string.open_usage_access_permission,
            cardId = SETTING_7,
            isChecked = mIsShowSetting7
        ) { newState ->
            mIsShowSetting7 = newState
            settingsSharedPref.saveCardShowedState(SETTING_7, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.open_overlay_permission,
            cardId = SETTING_8,
            isChecked = mIsShowSetting8
        ) { newState ->
            mIsShowSetting8 = newState
            settingsSharedPref.saveCardShowedState(SETTING_8, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.open_write_permission,
            cardId = SETTING_9,
            isChecked = mIsShowSetting9
        ) { newState ->
            mIsShowSetting9 = newState
            settingsSharedPref.saveCardShowedState(SETTING_9, newState)
        }

        CustomGroupDivider()

        CustomHideCardSettingSwitch(
            id = R.string.open_language_settings,
            cardId = SETTING_10,
            isChecked = mIsShowSetting10
        ) { newState ->
            mIsShowSetting10 = newState
            settingsSharedPref.saveCardShowedState(SETTING_10, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.open_date_and_time_settings,
            cardId = SETTING_11,
            isChecked = mIsShowSetting11
        ) { newState ->
            mIsShowSetting11 = newState
            settingsSharedPref.saveCardShowedState(SETTING_11, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.open_developer_options,
            cardId = SETTING_12,
            isChecked = mIsShowSetting12
        ) { newState ->
            mIsShowSetting12 = newState
            settingsSharedPref.saveCardShowedState(SETTING_12, newState)
        }

        CustomGroupDivider()

        val view = LocalView.current
        val primary = MaterialTheme.colorScheme.primary.toArgb()

        Button(
            onClick = {
                SnackbarUtil.snackbar(
                    view,
                    message = R.string.tap_to_confirm,
                    buttonText = R.string.confirm,
                    buttonColor = primary,
                    buttonClickListener = {
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
                    }
                )
            },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.VisibilityOff,
                contentDescription = stringResource(id = R.string.hide_all_options),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            CustomSpacerPadding()
            Text(text = stringResource(R.string.hide_all_options))
        }

        Button(
            onClick = {
                SnackbarUtil.snackbar(
                    view,
                    message = R.string.tap_to_confirm,
                    buttonText = R.string.confirm,
                    buttonColor = primary,
                    buttonClickListener = {
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
                    }
                )
            },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Visibility,
                contentDescription = stringResource(id = R.string.show_all_options),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            CustomSpacerPadding()
            Text(text = stringResource(R.string.show_all_options))
        }
    }
}

private fun onClickChangeAllCardsButton(isShow: Boolean) {
    val settingList = listOf(
        SETTING_1,
        SETTING_2,
        SETTING_3,
        SETTING_4,
        SETTING_5,
        SETTING_6,
        SETTING_7,
        SETTING_8,
        SETTING_9,
        SETTING_10,
        SETTING_11,
        SETTING_12
    )
    val settingsViewModel = SettingsSharedPref

    for (setting in settingList) {
        settingsViewModel.saveCardShowedState(setting, isShow)
    }
}