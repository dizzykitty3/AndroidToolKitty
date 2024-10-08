@file:Suppress("unused")

package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref

@Composable
fun CustomHideCardSettingSwitch(
    @StringRes text: Int,
    card: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) = Surface(
    shape = RoundedCornerShape(8.dp),
    color = MaterialTheme.colorScheme.surfaceContainerLow
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
    icon: ImageVector? = null,
    @StringRes title: Int,
    text: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) = CustomSwitchRow(icon, stringResource(title), text, checked, onCheckedChange)

@Composable
fun CustomSwitchRow(
    icon: ImageVector? = null,
    @StringRes title: Int,
    @StringRes text: Int,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) = CustomSwitchRow(icon, stringResource(title), stringResource(text), checked, onCheckedChange)

@Composable
fun CustomSwitchRow(
    icon: ImageVector? = null,
    title: String,
    text: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) = Surface(
    shape = RoundedCornerShape(8.dp),
    color = MaterialTheme.colorScheme.surfaceContainerLow
) {
    Column(Modifier.clickable { onCheckedChange(!checked) }) {
        SpacerPadding()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(R.string.customize_home_page)
                    )
                    IconAndTextPadding()
                }
                Column {
                    Text(title)
                    if (text != null) {
                        Description(text)
                    }
                }
            }
            SpacerPadding()
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange(it) }
            )
        }
        SpacerPadding()
    }
}