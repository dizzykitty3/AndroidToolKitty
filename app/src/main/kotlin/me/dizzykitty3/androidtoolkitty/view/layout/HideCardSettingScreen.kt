package me.dizzykitty3.androidtoolkitty.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomScreen
import me.dizzykitty3.androidtoolkitty.view.card.HideAndroidSystemSettingsOptionCard
import me.dizzykitty3.androidtoolkitty.view.card.HideCardSettingCard
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

private const val CARD_6 = "card_android_system_settings"

@Composable
fun HideCardSettingScreen() {
    val c = LocalContext.current
    CustomScreen {
        HideCardSettingCard()
        if (SettingsViewModel().getCardShowedState(c, CARD_6)) {
            HideAndroidSystemSettingsOptionCard()
        }
    }
}