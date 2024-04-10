package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.data.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomScreen
import me.dizzykitty3.androidtoolkitty.ui.card.EditAndroidSystemSettingsCardOptionsCard
import me.dizzykitty3.androidtoolkitty.ui.card.EditHomeScreenCard

private const val CARD_6 = "card_android_system_settings"

@Composable
fun EditHomePageScreen() {
    CustomScreen {
        EditHomeScreenCard()

        if (SettingsViewModel.getCardShowedState(CARD_6)) {
            EditAndroidSystemSettingsCardOptionsCard()
        }
    }
}