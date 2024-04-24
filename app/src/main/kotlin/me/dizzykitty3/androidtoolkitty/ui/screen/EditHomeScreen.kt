package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomScreen
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_6
import me.dizzykitty3.androidtoolkitty.ui.card.EditHomeCard
import me.dizzykitty3.androidtoolkitty.ui.card.EditSysSettingCard

@Composable
fun EditHomeScreen() {
    CustomScreen {
        EditHomeCard()

        val sysSettingCard by remember { mutableStateOf(SettingsSharedPref.getCardShowedState(CARD_6)) }
        if (sysSettingCard) EditSysSettingCard()
    }
}