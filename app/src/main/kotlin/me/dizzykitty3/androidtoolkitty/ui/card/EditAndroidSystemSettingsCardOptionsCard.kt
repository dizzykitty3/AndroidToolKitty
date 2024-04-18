package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomHideCardSettingSwitch
import me.dizzykitty3.androidtoolkitty.foundation.util.OsVersion

private const val SETTING_1 = "setting_display"
private const val SETTING_2 = "setting_auto_rotate"
private const val SETTING_3 = "setting_bluetooth"
private const val SETTING_4 = "setting_default_apps"
private const val SETTING_5 = "setting_battery_optimization"
private const val SETTING_6 = "setting_caption"
private const val SETTING_7 = "setting_locale"
private const val SETTING_8 = "setting_date_and_time"
private const val SETTING_9 = "setting_developer"

// TODO refactor this bullshit
@Composable
fun EditAndroidSystemSettingsCardOptionsCard() {
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
        var mIsShowSetting1 by remember { mutableStateOf(isShowSetting1) }
        var mIsShowSetting2 by remember { mutableStateOf(isShowSetting2) }
        var mIsShowSetting3 by remember { mutableStateOf(isShowSetting3) }
        var mIsShowSetting4 by remember { mutableStateOf(isShowSetting4) }
        var mIsShowSetting5 by remember { mutableStateOf(isShowSetting5) }
        var mIsShowSetting6 by remember { mutableStateOf(isShowSetting6) }
        var mIsShowSetting7 by remember { mutableStateOf(isShowSetting7) }
        var mIsShowSetting8 by remember { mutableStateOf(isShowSetting8) }
        var mIsShowSetting9 by remember { mutableStateOf(isShowSetting9) }
        CustomHideCardSettingSwitch(
            resId = R.string.open_display_settings,
            cardId = SETTING_1,
            isChecked = mIsShowSetting1
        ) { newState ->
            mIsShowSetting1 = newState
            settingsSharedPref.saveCardShowedState(SETTING_1, newState)
        }
        if (OsVersion.android12()) {
            CustomHideCardSettingSwitch(
                resId = R.string.open_auto_rotate_settings,
                cardId = SETTING_2,
                isChecked = mIsShowSetting2
            ) { newState ->
                mIsShowSetting2 = newState
                settingsSharedPref.saveCardShowedState(SETTING_2, newState)
            }
        }
        CustomHideCardSettingSwitch(
            resId = R.string.open_bluetooth_settings,
            cardId = SETTING_3,
            isChecked = mIsShowSetting3
        ) { newState ->
            mIsShowSetting3 = newState
            settingsSharedPref.saveCardShowedState(SETTING_3, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.open_default_apps_settings,
            cardId = SETTING_4,
            isChecked = mIsShowSetting4
        ) { newState ->
            mIsShowSetting4 = newState
            settingsSharedPref.saveCardShowedState(SETTING_4, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.open_battery_optimization_settings,
            cardId = SETTING_5,
            isChecked = mIsShowSetting5
        ) { newState ->
            mIsShowSetting5 = newState
            settingsSharedPref.saveCardShowedState(SETTING_5, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.open_caption_preferences,
            cardId = SETTING_6,
            isChecked = mIsShowSetting6
        ) { newState ->
            mIsShowSetting6 = newState
            settingsSharedPref.saveCardShowedState(SETTING_6, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.open_language_settings,
            cardId = SETTING_7,
            isChecked = mIsShowSetting7
        ) { newState ->
            mIsShowSetting7 = newState
            settingsSharedPref.saveCardShowedState(SETTING_7, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.open_date_and_time_settings,
            cardId = SETTING_8,
            isChecked = mIsShowSetting8
        ) { newState ->
            mIsShowSetting8 = newState
            settingsSharedPref.saveCardShowedState(SETTING_8, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.open_developer_options,
            cardId = SETTING_9,
            isChecked = mIsShowSetting9
        ) { newState ->
            mIsShowSetting9 = newState
            settingsSharedPref.saveCardShowedState(SETTING_9, newState)
        }
    }
}