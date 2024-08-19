package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref

@Composable
fun CustomHideCardSettingSwitch(
    @StringRes text: Int,
    card: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier.clickable {
            onCheckedChange(!isChecked)
            SettingsSharedPref.saveShownState(card, !isChecked)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) { Text(stringResource(text)) }
        Column {
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun CustomSwitchRow(
    @StringRes title: Int,
    text: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) = CustomSwitchRow(stringResource(title), text, checked, onCheckedChange)

@Composable
fun CustomSwitchRow(
    @StringRes title: Int,
    @StringRes text: Int,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) = CustomSwitchRow(
    stringResource(title),
    stringResource(text),
    checked,
    onCheckedChange
)

@Composable
private fun CustomSwitchRow(
    title: String,
    text: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(shape = RoundedCornerShape(8.dp)) {
        Box(Modifier.clickable { onCheckedChange(!checked) }) {
            Box(Modifier.padding(end = 8.dp)) {
                SpacerPadding()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(title)
                        if (text != null) {
                            Text(
                                text,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
                            )
                        }
                    }
                    CardSpacePadding()
                    Switch(
                        checked = checked,
                        onCheckedChange = { onCheckedChange(it) }
                    )
                }
                SpacerPadding()
            }
        }
    }
}