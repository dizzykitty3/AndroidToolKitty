package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPasteSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.Actions
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun ClipboardCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.Outlined.ContentPasteSearch,
        title = c.getString(R.string.clipboard),
        id = "card_clipboard"
    ) {
        val isShowHintText = !SettingsViewModel(c).getHaveOpenedSettingsScreen()
        CustomSpacerPadding()
        Button(
            onClick = {
                Actions.onClearClipboardButton()
            },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Text(
                text = LocalContext.current.getString(R.string.clear_clipboard)
            )
        }
        if (isShowHintText) {
            CustomSpacerPadding()
            Text(
                text = c.getString(R.string.you_can_turn_on_clear_clipboard_on_launch_in_settings_screen)
            )
        }
    }
}