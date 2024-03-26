package me.dizzykitty3.androidtoolkitty.common.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun CustomHideCardSettingSwitch(
    text: String,
    cardId: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val c = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onCheckedChange(!isChecked)
            SettingsViewModel().saveCardShowedState(c, cardId, !isChecked)
        }
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}