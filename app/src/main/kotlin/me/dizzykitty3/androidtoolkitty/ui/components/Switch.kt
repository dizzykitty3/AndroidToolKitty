package me.dizzykitty3.androidtoolkitty.ui.components

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
    @StringRes text: Int,
    card: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val settingsSharedPref = remember { SettingsSharedPref }

    Row(
        Modifier.clickable {
            onCheckedChange(!isChecked)
            settingsSharedPref.saveCardShowedState(card, !isChecked)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(stringResource(text))
        }
        Column {
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun CustomSwitchRow(@StringRes text: Int, checked: Boolean, onCheckedChange: (Boolean) -> Unit) =
    CustomSwitchRow(stringResource(text), checked, onCheckedChange)

@Composable
fun CustomSwitchRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier.clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(text)
        }
        Column {
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange(it) }
            )
        }
    }
}