package me.dizzykitty3.androidtoolkitty.ui.view.edit_home_screen

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
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_1
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_10
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_11
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_12
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_2
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_3
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_4
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_5
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_6
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_7
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_8
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_9
import me.dizzykitty3.androidtoolkitty.foundation.util.SnackbarUtil
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomHideCardSettingSwitch
import me.dizzykitty3.androidtoolkitty.ui.component.CustomScreen
import me.dizzykitty3.androidtoolkitty.ui.component.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.component.SpacerPadding

@Composable
fun EditHomeScreen() {
    CustomScreen {
        val settingsSharedPref = remember { SettingsSharedPref }
        val isShowCard5 = settingsSharedPref.getCardShowedState(CARD_5)
        var mIsShowCard5 by remember { mutableStateOf(isShowCard5) }

        CustomCard(
            titleRes = R.string.customize_my_home_page
        ) {
            val view = LocalView.current

            val isShowCard1 = settingsSharedPref.getCardShowedState(CARD_1)
            val isShowCard2 = settingsSharedPref.getCardShowedState(CARD_2)
            val isShowCard3 = settingsSharedPref.getCardShowedState(CARD_3)
            val isShowCard4 = settingsSharedPref.getCardShowedState(CARD_4)
            val isShowCard6 = settingsSharedPref.getCardShowedState(CARD_6)
            val isShowCard7 = settingsSharedPref.getCardShowedState(CARD_7)
            val isShowCard8 = settingsSharedPref.getCardShowedState(CARD_8)
            val isShowCard9 = settingsSharedPref.getCardShowedState(CARD_9)
            val isShowCard10 = settingsSharedPref.getCardShowedState(CARD_10)
            val isShowCard11 = settingsSharedPref.getCardShowedState(CARD_11)
            val isShowCard12 = settingsSharedPref.getCardShowedState(CARD_12)

            var mIsShowCard1 by remember { mutableStateOf(isShowCard1) }
            var mIsShowCard2 by remember { mutableStateOf(isShowCard2) }
            var mIsShowCard3 by remember { mutableStateOf(isShowCard3) }
            var mIsShowCard4 by remember { mutableStateOf(isShowCard4) }
            var mIsShowCard6 by remember { mutableStateOf(isShowCard6) }
            var mIsShowCard7 by remember { mutableStateOf(isShowCard7) }
            var mIsShowCard8 by remember { mutableStateOf(isShowCard8) }
            var mIsShowCard9 by remember { mutableStateOf(isShowCard9) }
            var mIsShowCard10 by remember { mutableStateOf(isShowCard10) }
            var mIsShowCard11 by remember { mutableStateOf(isShowCard11) }
            var mIsShowCard12 by remember { mutableStateOf(isShowCard12) }

            CustomHideCardSettingSwitch(
                textRes = R.string.year_progress,
                card = CARD_1,
                isChecked = mIsShowCard1
            ) { newState ->
                mIsShowCard1 = newState
                settingsSharedPref.saveCardShowedState(CARD_1, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.volume,
                card = CARD_2,
                isChecked = mIsShowCard2
            ) { newState ->
                mIsShowCard2 = newState
                settingsSharedPref.saveCardShowedState(CARD_2, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.clipboard,
                card = CARD_3,
                isChecked = mIsShowCard3
            ) { newState ->
                mIsShowCard3 = newState
                settingsSharedPref.saveCardShowedState(CARD_3, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.url,
                card = CARD_4,
                isChecked = mIsShowCard4
            ) { newState ->
                mIsShowCard4 = newState
                settingsSharedPref.saveCardShowedState(CARD_4, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.system_settings,
                card = CARD_5,
                isChecked = mIsShowCard5
            ) { newState ->
                mIsShowCard5 = newState
                settingsSharedPref.saveCardShowedState(CARD_5, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.wheel_of_fortune,
                card = CARD_6,
                isChecked = mIsShowCard6
            ) { newState ->
                mIsShowCard6 = newState
                settingsSharedPref.saveCardShowedState(CARD_6, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.bluetooth_devices,
                card = CARD_7,
                isChecked = mIsShowCard7
            ) { newState ->
                mIsShowCard7 = newState
                settingsSharedPref.saveCardShowedState(CARD_7, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.unicode,
                card = CARD_8,
                isChecked = mIsShowCard8
            ) { newState ->
                mIsShowCard8 = newState
                settingsSharedPref.saveCardShowedState(CARD_8, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.check_app_on_market,
                card = CARD_9,
                isChecked = mIsShowCard9
            ) { newState ->
                mIsShowCard9 = newState
                settingsSharedPref.saveCardShowedState(CARD_9, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.google_maps,
                card = CARD_10,
                isChecked = mIsShowCard10
            ) { newState ->
                mIsShowCard10 = newState
                settingsSharedPref.saveCardShowedState(CARD_10, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.android_versions,
                card = CARD_11,
                isChecked = mIsShowCard11
            ) { newState ->
                mIsShowCard11 = newState
                settingsSharedPref.saveCardShowedState(CARD_11, newState)
            }
            CustomHideCardSettingSwitch(
                textRes = R.string.font_weight_test,
                card = CARD_12,
                isChecked = mIsShowCard12
            ) { newState ->
                mIsShowCard12 = newState
                settingsSharedPref.saveCardShowedState(CARD_12, newState)
            }

            GroupDivider()

            val primary = MaterialTheme.colorScheme.primary.toArgb()
            val showSnackbarToConfirm = settingsSharedPref.showSnackbar

            Button(
                onClick = {
                    if (showSnackbarToConfirm) {
                        SnackbarUtil.snackbar(
                            view,
                            messageRes = R.string.tap_to_apply,
                            buttonTextRes = R.string.apply,
                            buttonColor = primary,
                            buttonClickListener = {
                                onClickChangeAllCardsButton(false)
                                mIsShowCard1 = false
                                mIsShowCard2 = false
                                mIsShowCard3 = false
                                mIsShowCard4 = false
                                mIsShowCard5 = false
                                mIsShowCard6 = false
                                mIsShowCard7 = false
                                mIsShowCard8 = false
                                mIsShowCard9 = false
                                mIsShowCard10 = false
                                mIsShowCard11 = false
                                mIsShowCard12 = false
                            }
                        )
                    } else {
                        onClickChangeAllCardsButton(false)
                        mIsShowCard1 = false
                        mIsShowCard2 = false
                        mIsShowCard3 = false
                        mIsShowCard4 = false
                        mIsShowCard5 = false
                        mIsShowCard6 = false
                        mIsShowCard7 = false
                        mIsShowCard8 = false
                        mIsShowCard9 = false
                        mIsShowCard10 = false
                        mIsShowCard11 = false
                        mIsShowCard12 = false
                    }
                },
                elevation = ButtonDefaults.buttonElevation(1.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.VisibilityOff,
                    contentDescription = stringResource(id = R.string.hide_all_cards),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                SpacerPadding()
                Text(text = stringResource(R.string.hide_all_cards))
            }

            Button(
                onClick = {
                    if (showSnackbarToConfirm) {
                        SnackbarUtil.snackbar(
                            view,
                            messageRes = R.string.tap_to_apply,
                            buttonTextRes = R.string.apply,
                            buttonColor = primary,
                            buttonClickListener = {
                                onClickChangeAllCardsButton(true)
                                mIsShowCard1 = true
                                mIsShowCard2 = true
                                mIsShowCard3 = true
                                mIsShowCard4 = true
                                mIsShowCard5 = true
                                mIsShowCard6 = true
                                mIsShowCard7 = true
                                mIsShowCard8 = true
                                mIsShowCard9 = true
                                mIsShowCard10 = true
                                mIsShowCard11 = true
                                mIsShowCard12 = true
                            }
                        )
                    } else {
                        onClickChangeAllCardsButton(true)
                        mIsShowCard1 = true
                        mIsShowCard2 = true
                        mIsShowCard3 = true
                        mIsShowCard4 = true
                        mIsShowCard5 = true
                        mIsShowCard6 = true
                        mIsShowCard7 = true
                        mIsShowCard8 = true
                        mIsShowCard9 = true
                        mIsShowCard10 = true
                        mIsShowCard11 = true
                        mIsShowCard12 = true
                    }
                },
                elevation = ButtonDefaults.buttonElevation(1.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Visibility,
                    contentDescription = stringResource(id = R.string.show_all_cards),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                SpacerPadding()
                Text(text = stringResource(R.string.show_all_cards))
            }
        }

        if (mIsShowCard5) EditSysSettingsCard()
    }
}

private fun onClickChangeAllCardsButton(isShow: Boolean) {
    val cardList = listOf(
        CARD_1,
        CARD_2,
        CARD_3,
        CARD_4,
        CARD_5,
        CARD_6,
        CARD_7,
        CARD_8,
        CARD_9,
        CARD_10,
        CARD_11,
        CARD_12
    )
    val settingsViewModel = SettingsSharedPref

    for (card in cardList) {
        settingsViewModel.saveCardShowedState(card, isShow)
    }
}