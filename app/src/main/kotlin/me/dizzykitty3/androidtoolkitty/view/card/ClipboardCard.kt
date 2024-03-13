package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPasteSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        title = LocalContext.current.getString(R.string.clipboard),
        id = "card_clipboard"
    ) {
        val mIsAutoClearClipboard = SettingsViewModel(c).getIsAutoClearClipboard()
        var isAutoClearClipboard by remember { mutableStateOf(mIsAutoClearClipboard) }
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isAutoClearClipboard = !isAutoClearClipboard
                SettingsViewModel(c).setIsAutoClearClipboard(isAutoClearClipboard)
            }
        ) {
            Text(
                text = c.getString(R.string.clear_clipboard_on_launch)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isAutoClearClipboard,
                onCheckedChange = {
                    isAutoClearClipboard = it
                    SettingsViewModel(c).setIsAutoClearClipboard(it)
                }
            )
        }
    }
}