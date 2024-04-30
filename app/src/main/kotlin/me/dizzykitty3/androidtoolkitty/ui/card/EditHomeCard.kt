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

/**
 * @see me.dizzykitty3.androidtoolkitty.ui.screen.HomeScreen
 */
// TODO refactor this bullshit
@Composable
fun EditHomeCard() {
    CustomCard(
        title = R.string.customize_my_home_page
    ) {
        val view = LocalView.current
        val settingsSharedPref = remember { SettingsSharedPref }

        val isShowCard1 = settingsSharedPref.getCardShowedState(CARD_1)
        val isShowCard2 = settingsSharedPref.getCardShowedState(CARD_2)
        val isShowCard3 = settingsSharedPref.getCardShowedState(CARD_3)
        val isShowCard4 = settingsSharedPref.getCardShowedState(CARD_4)
        val isShowCard5 = settingsSharedPref.getCardShowedState(CARD_5)
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
        var mIsShowCard5 by remember { mutableStateOf(isShowCard5) }
        var mIsShowCard6 by remember { mutableStateOf(isShowCard6) }
        var mIsShowCard7 by remember { mutableStateOf(isShowCard7) }
        var mIsShowCard8 by remember { mutableStateOf(isShowCard8) }
        var mIsShowCard9 by remember { mutableStateOf(isShowCard9) }
        var mIsShowCard10 by remember { mutableStateOf(isShowCard10) }
        var mIsShowCard11 by remember { mutableStateOf(isShowCard11) }
        var mIsShowCard12 by remember { mutableStateOf(isShowCard12) }

        CustomHideCardSettingSwitch(
            id = R.string.year_progress,
            cardId = CARD_1,
            isChecked = mIsShowCard1
        ) { newState ->
            mIsShowCard1 = newState
            settingsSharedPref.saveCardShowedState(CARD_1, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.volume,
            cardId = CARD_2,
            isChecked = mIsShowCard2
        ) { newState ->
            mIsShowCard2 = newState
            settingsSharedPref.saveCardShowedState(CARD_2, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.clipboard,
            cardId = CARD_3,
            isChecked = mIsShowCard3
        ) { newState ->
            mIsShowCard3 = newState
            settingsSharedPref.saveCardShowedState(CARD_3, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.url,
            cardId = CARD_4,
            isChecked = mIsShowCard4
        ) { newState ->
            mIsShowCard4 = newState
            settingsSharedPref.saveCardShowedState(CARD_4, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.system_settings,
            cardId = CARD_5,
            isChecked = mIsShowCard5
        ) { newState ->
            mIsShowCard5 = newState
            settingsSharedPref.saveCardShowedState(CARD_5, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.wheel_of_fortune,
            cardId = CARD_6,
            isChecked = mIsShowCard6
        ) { newState ->
            mIsShowCard6 = newState
            settingsSharedPref.saveCardShowedState(CARD_6, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.bluetooth_devices,
            cardId = CARD_7,
            isChecked = mIsShowCard7
        ) { newState ->
            mIsShowCard7 = newState
            settingsSharedPref.saveCardShowedState(CARD_7, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.unicode,
            cardId = CARD_8,
            isChecked = mIsShowCard8
        ) { newState ->
            mIsShowCard8 = newState
            settingsSharedPref.saveCardShowedState(CARD_8, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.check_app_on_market,
            cardId = CARD_9,
            isChecked = mIsShowCard9
        ) { newState ->
            mIsShowCard9 = newState
            settingsSharedPref.saveCardShowedState(CARD_9, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.google_maps,
            cardId = CARD_10,
            isChecked = mIsShowCard10
        ) { newState ->
            mIsShowCard10 = newState
            settingsSharedPref.saveCardShowedState(CARD_10, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.android_versions,
            cardId = CARD_11,
            isChecked = mIsShowCard11
        ) { newState ->
            mIsShowCard11 = newState
            settingsSharedPref.saveCardShowedState(CARD_11, newState)
        }
        CustomHideCardSettingSwitch(
            id = R.string.font_weight,
            cardId = CARD_12,
            isChecked = mIsShowCard12
        ) { newState ->
            mIsShowCard12 = newState
            settingsSharedPref.saveCardShowedState(CARD_12, newState)
        }

        CustomGroupDivider()

        val primary = MaterialTheme.colorScheme.primary.toArgb()

        Button(
            onClick = {
                SnackbarUtil.snackbar(
                    view,
                    message = R.string.tap_to_confirm,
                    buttonText = android.R.string.ok,
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
            },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.VisibilityOff,
                contentDescription = stringResource(id = R.string.hide_all_cards),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            CustomSpacerPadding()
            Text(text = stringResource(R.string.hide_all_cards))
        }

        Button(
            onClick = {
                SnackbarUtil.snackbar(
                    view,
                    message = R.string.tap_to_confirm,
                    buttonText = android.R.string.ok,
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
            },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Visibility,
                contentDescription = stringResource(id = R.string.show_all_cards),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            CustomSpacerPadding()
            Text(text = stringResource(R.string.show_all_cards))
        }
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