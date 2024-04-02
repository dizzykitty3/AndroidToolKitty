package me.dizzykitty3.androidtoolkitty.view.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomScreen
import me.dizzykitty3.androidtoolkitty.view.card.EditAndroidSystemSettingsCardOptionsCard
import me.dizzykitty3.androidtoolkitty.view.card.EditHomePageCard
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

private const val CARD_6 = "card_android_system_settings"

@Composable
fun HideCardSettingScreen() {
    val context = LocalContext.current
    CustomScreen {
        EditHomePageCard()

        if (SettingsViewModel().getCardShowedState(context, CARD_6)) {
            EditAndroidSystemSettingsCardOptionsCard()
        }
    }
}