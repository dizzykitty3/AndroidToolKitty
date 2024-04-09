package me.dizzykitty3.androidtoolkitty.view.layout

import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomScreen
import me.dizzykitty3.androidtoolkitty.view.card.EditAndroidSystemSettingsCardOptionsCard
import me.dizzykitty3.androidtoolkitty.view.card.EditHomeScreenCard
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

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