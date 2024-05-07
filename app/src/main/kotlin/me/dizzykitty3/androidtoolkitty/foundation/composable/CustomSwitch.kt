package me.dizzykitty3.androidtoolkitty.foundation.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref

@Composable
fun CustomHideCardSettingSwitch(
    @StringRes id: Int,
    cardId: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val settingsSharedPref = remember { SettingsSharedPref }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onCheckedChange(!isChecked)
            settingsSharedPref.saveCardShowedState(cardId, !isChecked)
        }
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(id = id))
        }
        Column {
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}