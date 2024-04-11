package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomHideCardSettingSwitch

private const val CARD_1 = "card_year_progress"
private const val CARD_2 = "card_volume"
private const val CARD_3 = "card_clipboard"
private const val CARD_4 = "card_url"
private const val CARD_6 = "card_android_system_settings"
private const val CARD_7 = "card_unicode"
private const val CARD_8 = "card_google_maps"
private const val CARD_9 = "card_open_app_on_google_play"
private const val CARD_10 = "card_android_versions"
private const val CARD_11 = "card_lucky_wheel"
private const val CARD_12 = "card_bluetooth_devices"

@Composable
fun EditHomeScreenCard() {
    CustomCard(
        title = R.string.customize_my_home_page
    ) {
        val settingsViewModel = remember { SettingsViewModel }

        val isShowCard1 = settingsViewModel.getCardShowedState(CARD_1)
        val isShowCard2 = settingsViewModel.getCardShowedState(CARD_2)
        val isShowCard3 = settingsViewModel.getCardShowedState(CARD_3)
        val isShowCard4 = settingsViewModel.getCardShowedState(CARD_4)
        val isShowCard6 = settingsViewModel.getCardShowedState(CARD_6)
        val isShowCard7 = settingsViewModel.getCardShowedState(CARD_7)
        val isShowCard8 = settingsViewModel.getCardShowedState(CARD_8)
        val isShowCard9 = settingsViewModel.getCardShowedState(CARD_9)
        val isShowCard10 = settingsViewModel.getCardShowedState(CARD_10)
        val isShowCard11 = settingsViewModel.getCardShowedState(CARD_11)
        val isShowCard12 = settingsViewModel.getCardShowedState(CARD_12)

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
            resId = R.string.year_progress,
            cardId = CARD_1,
            isChecked = mIsShowCard1
        ) { newState ->
            mIsShowCard1 = newState
            settingsViewModel.saveCardShowedState(CARD_1, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.volume,
            cardId = CARD_2,
            isChecked = mIsShowCard2
        ) { newState ->
            mIsShowCard2 = newState
            settingsViewModel.saveCardShowedState(CARD_2, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.clipboard,
            cardId = CARD_3,
            isChecked = mIsShowCard3
        ) { newState ->
            mIsShowCard3 = newState
            settingsViewModel.saveCardShowedState(CARD_3, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.url,
            cardId = CARD_4,
            isChecked = mIsShowCard4
        ) { newState ->
            mIsShowCard4 = newState
            settingsViewModel.saveCardShowedState(CARD_4, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.android_system_settings,
            cardId = CARD_6,
            isChecked = mIsShowCard6
        ) { newState ->
            mIsShowCard6 = newState
            settingsViewModel.saveCardShowedState(CARD_6, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.unicode,
            cardId = CARD_7,
            isChecked = mIsShowCard7
        ) { newState ->
            mIsShowCard7 = newState
            settingsViewModel.saveCardShowedState(CARD_7, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.google_maps,
            cardId = CARD_8,
            isChecked = mIsShowCard8
        ) { newState ->
            mIsShowCard8 = newState
            settingsViewModel.saveCardShowedState(CARD_8, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.open_app_on_google_play,
            cardId = CARD_9,
            isChecked = mIsShowCard9
        ) { newState ->
            mIsShowCard9 = newState
            settingsViewModel.saveCardShowedState(CARD_9, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.android_versions,
            cardId = CARD_10,
            isChecked = mIsShowCard10
        ) { newState ->
            mIsShowCard10 = newState
            settingsViewModel.saveCardShowedState(CARD_10, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.lucky_spinning_wheel,
            cardId = CARD_11,
            isChecked = mIsShowCard11
        ) { newState ->
            mIsShowCard11 = newState
            settingsViewModel.saveCardShowedState(CARD_11, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.bluetooth_devices,
            cardId = CARD_12,
            isChecked = mIsShowCard12
        ) { newState ->
            mIsShowCard12 = newState
            settingsViewModel.saveCardShowedState(CARD_12, newState)
        }

        CustomGroupDivider()

        Button(
            onClick = {
                onClickChangeAllCardsButton(false)
                mIsShowCard1 = false
                mIsShowCard2 = false
                mIsShowCard3 = false
                mIsShowCard4 = false
                mIsShowCard6 = false
                mIsShowCard7 = false
                mIsShowCard8 = false
                mIsShowCard9 = false
                mIsShowCard10 = false
                mIsShowCard11 = false
                mIsShowCard12 = false
            }
        ) {
            Text(text = stringResource(R.string.hide_all_cards))
        }

        Button(
            onClick = {
                onClickChangeAllCardsButton(true)
                mIsShowCard1 = true
                mIsShowCard2 = true
                mIsShowCard3 = true
                mIsShowCard4 = true
                mIsShowCard6 = true
                mIsShowCard7 = true
                mIsShowCard8 = true
                mIsShowCard9 = true
                mIsShowCard10 = true
                mIsShowCard11 = true
                mIsShowCard12 = true
            }
        ) {
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
        CARD_6,
        CARD_7,
        CARD_8,
        CARD_9,
        CARD_10,
        CARD_11,
        CARD_12
    )
    val settingsViewModel = SettingsViewModel

    for (card in cardList) {
        settingsViewModel.saveCardShowedState(card, isShow)
    }
}