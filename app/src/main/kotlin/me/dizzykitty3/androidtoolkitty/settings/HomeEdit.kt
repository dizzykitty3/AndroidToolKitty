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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.CARD_1
import me.dizzykitty3.androidtoolkitty.CARD_10
import me.dizzykitty3.androidtoolkitty.CARD_11
import me.dizzykitty3.androidtoolkitty.CARD_12
import me.dizzykitty3.androidtoolkitty.CARD_2
import me.dizzykitty3.androidtoolkitty.CARD_3
import me.dizzykitty3.androidtoolkitty.CARD_4
import me.dizzykitty3.androidtoolkitty.CARD_5
import me.dizzykitty3.androidtoolkitty.CARD_6
import me.dizzykitty3.androidtoolkitty.CARD_7
import me.dizzykitty3.androidtoolkitty.CARD_8
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CardSpacePadding
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomHideCardSettingSwitch
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.ItalicText
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.HapticUtil.hapticFeedback

@Composable
fun HomeEdit(settingsViewModel: SettingsViewModel) {
    Screen {
        val sp = remember { SettingsSharedPref }
        var mIsShowCard5 by remember { mutableStateOf(sp.getCardShowedState(CARD_5)) }

        Text(buildAnnotatedString { ItalicText(R.string.scroll_down) })
        CardSpacePadding()

        Card(R.string.customize_my_home_page) {
            val view = LocalView.current
            var mIsShowCard1 by remember { mutableStateOf(sp.getCardShowedState(CARD_1)) }
            var mIsShowCard2 by remember { mutableStateOf(sp.getCardShowedState(CARD_2)) }
            var mIsShowCard3 by remember { mutableStateOf(sp.getCardShowedState(CARD_3)) }
            var mIsShowCard4 by remember { mutableStateOf(sp.getCardShowedState(CARD_4)) }
            var mIsShowCard6 by remember { mutableStateOf(sp.getCardShowedState(CARD_6)) }
            var mIsShowCard7 by remember { mutableStateOf(sp.getCardShowedState(CARD_7)) }
            var mIsShowCard8 by remember { mutableStateOf(sp.getCardShowedState(CARD_8)) }
            var mIsShowCard10 by remember { mutableStateOf(sp.getCardShowedState(CARD_10)) }
            var mIsShowCard11 by remember { mutableStateOf(sp.getCardShowedState(CARD_11)) }
            var mIsShowCard12 by remember { mutableStateOf(sp.getCardShowedState(CARD_12)) }

            CustomHideCardSettingSwitch(
                text = R.string.year_progress,
                card = CARD_1,
                isChecked = mIsShowCard1
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard1 = newState
                sp.saveCardShowedState(CARD_1, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.volume,
                card = CARD_2,
                isChecked = mIsShowCard2
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard2 = newState
                sp.saveCardShowedState(CARD_2, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.clipboard,
                card = CARD_3,
                isChecked = mIsShowCard3
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard3 = newState
                sp.saveCardShowedState(CARD_3, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.url,
                card = CARD_4,
                isChecked = mIsShowCard4
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard4 = newState
                sp.saveCardShowedState(CARD_4, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.system_settings,
                card = CARD_5,
                isChecked = mIsShowCard5
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard5 = newState
                sp.saveCardShowedState(CARD_5, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.wheel_of_fortune,
                card = CARD_6,
                isChecked = mIsShowCard6
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard6 = newState
                sp.saveCardShowedState(CARD_6, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.bluetooth_devices,
                card = CARD_7,
                isChecked = mIsShowCard7
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard7 = newState
                sp.saveCardShowedState(CARD_7, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.unicode,
                card = CARD_8,
                isChecked = mIsShowCard8
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard8 = newState
                sp.saveCardShowedState(CARD_8, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.android_versions,
                card = CARD_11,
                isChecked = mIsShowCard11
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard11 = newState
                sp.saveCardShowedState(CARD_11, newState)
            }
            CustomHideCardSettingSwitch(
                text = R.string.font_weight_test,
                card = CARD_12,
                isChecked = mIsShowCard12
            ) { newState ->
                view.hapticFeedback()
                mIsShowCard12 = newState
                sp.saveCardShowedState(CARD_12, newState)
            }

            GroupDivider()

            Button(
                {
                    view.hapticFeedback()
                    onClickChangeAllCardsButton(false)
                    mIsShowCard1 = false
                    mIsShowCard2 = false
                    mIsShowCard3 = false
                    mIsShowCard4 = false
                    mIsShowCard5 = false
                    mIsShowCard6 = false
                    mIsShowCard7 = false
                    mIsShowCard8 = false
                    mIsShowCard10 = false
                    mIsShowCard11 = false
                    mIsShowCard12 = false
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
                {
                    view.hapticFeedback()
                    onClickChangeAllCardsButton(true)
                    mIsShowCard1 = true
                    mIsShowCard2 = true
                    mIsShowCard3 = true
                    mIsShowCard4 = true
                    mIsShowCard5 = true
                    mIsShowCard6 = true
                    mIsShowCard7 = true
                    mIsShowCard8 = true
                    mIsShowCard10 = true
                    mIsShowCard11 = true
                    mIsShowCard12 = true
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

        if (mIsShowCard5) SysSettingsCardEdit()
    }
}

private fun onClickChangeAllCardsButton(isShow: Boolean) {
    val cardList = listOf(
        CARD_1, CARD_2, CARD_3, CARD_4, CARD_5, CARD_6,
        CARD_7, CARD_8, CARD_10, CARD_11, CARD_12
    )
    cardList.forEach { card ->
        SettingsSharedPref.saveCardShowedState(card, isShow)
    }
}