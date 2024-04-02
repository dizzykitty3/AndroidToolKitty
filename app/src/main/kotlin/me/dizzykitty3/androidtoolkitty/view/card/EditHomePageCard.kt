package me.dizzykitty3.androidtoolkitty.view.card

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCardNoIcon
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomHideCardSettingSwitch
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

private const val CARD_1 = "card_year_progress"
private const val CARD_2 = "card_volume"
private const val CARD_3 = "card_clipboard"
private const val CARD_4 = "card_url"
private const val CARD_5 = "card_social_media_profile"
private const val CARD_6 = "card_android_system_settings"
private const val CARD_7 = "card_unicode"
private const val CARD_8 = "card_google_maps"
private const val CARD_9 = "card_open_app_on_google_play"
private const val CARD_10 = "card_android_versions"

@Composable
fun EditHomePageCard() {
    CustomCardNoIcon(
        title = R.string.customize_my_home_page
    ) {
        val context = LocalContext.current

        val isShowCard1 = SettingsViewModel().getCardShowedState(context, CARD_1)
        val isShowCard2 = SettingsViewModel().getCardShowedState(context, CARD_2)
        val isShowCard3 = SettingsViewModel().getCardShowedState(context, CARD_3)
        val isShowCard4 = SettingsViewModel().getCardShowedState(context, CARD_4)
        val isShowCard5 = SettingsViewModel().getCardShowedState(context, CARD_5)
        val isShowCard6 = SettingsViewModel().getCardShowedState(context, CARD_6)
        val isShowCard7 = SettingsViewModel().getCardShowedState(context, CARD_7)
        val isShowCard8 = SettingsViewModel().getCardShowedState(context, CARD_8)
        val isShowCard9 = SettingsViewModel().getCardShowedState(context, CARD_9)
        val isShowCard10 = SettingsViewModel().getCardShowedState(context, CARD_10)
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
        CustomHideCardSettingSwitch(
            resId = R.string.year_progress,
            cardId = CARD_1,
            isChecked = mIsShowCard1
        ) { newState ->
            mIsShowCard1 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_1, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.volume,
            cardId = CARD_2,
            isChecked = mIsShowCard2
        ) { newState ->
            mIsShowCard2 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_2, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.clipboard,
            cardId = CARD_3,
            isChecked = mIsShowCard3
        ) { newState ->
            mIsShowCard3 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_3, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.url,
            cardId = CARD_4,
            isChecked = mIsShowCard4
        ) { newState ->
            mIsShowCard4 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_4, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.social_media_profile,
            cardId = CARD_5,
            isChecked = mIsShowCard5
        ) { newState ->
            mIsShowCard5 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_5, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.android_system_settings,
            cardId = CARD_6,
            isChecked = mIsShowCard6
        ) { newState ->
            mIsShowCard6 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_6, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.unicode,
            cardId = CARD_7,
            isChecked = mIsShowCard7
        ) { newState ->
            mIsShowCard7 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_7, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.google_maps,
            cardId = CARD_8,
            isChecked = mIsShowCard8
        ) { newState ->
            mIsShowCard8 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_8, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.open_app_on_google_play,
            cardId = CARD_9,
            isChecked = mIsShowCard9
        ) { newState ->
            mIsShowCard9 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_9, newState)
        }
        CustomHideCardSettingSwitch(
            resId = R.string.android_versions,
            cardId = CARD_10,
            isChecked = mIsShowCard10
        ) { newState ->
            mIsShowCard10 = newState
            SettingsViewModel().saveCardShowedState(context, CARD_10, newState)
        }

        CustomGroupDivider()

        Button(
            onClick = {
                onClickChangeAllCardsButton(context, false)
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
            }
        ) {
            Text(text = stringResource(R.string.hide_all_cards))
        }

        Button(
            onClick = {
                onClickChangeAllCardsButton(context, true)
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
            }
        ) {
            Text(text = stringResource(R.string.show_all_cards))
        }
    }
}

private fun onClickChangeAllCardsButton(c: Context, isShow: Boolean) {
    SettingsViewModel().saveCardShowedState(c, CARD_1, isShow)
    SettingsViewModel().saveCardShowedState(c, CARD_2, isShow)
    SettingsViewModel().saveCardShowedState(c, CARD_3, isShow)
    SettingsViewModel().saveCardShowedState(c, CARD_4, isShow)
    SettingsViewModel().saveCardShowedState(c, CARD_5, isShow)
    SettingsViewModel().saveCardShowedState(c, CARD_6, isShow)
    SettingsViewModel().saveCardShowedState(c, CARD_7, isShow)
    SettingsViewModel().saveCardShowedState(c, CARD_8, isShow)
    SettingsViewModel().saveCardShowedState(c, CARD_9, isShow)
    SettingsViewModel().saveCardShowedState(c, CARD_10, isShow)
}