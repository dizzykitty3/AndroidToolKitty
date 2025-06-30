package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
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
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) = CustomSwitchRow(icon, stringResource(title), checked, onCheckedChange)

@Composable
fun CustomSwitchRow(
    icon: ImageVector? = null,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) = Surface(
    shape = RoundedCornerShape(8.dp),
    color = MaterialTheme.colorScheme.surfaceContainerLow
) {
    Column(
        Modifier
            .height(dimensionResource(R.dimen.height_setting_row))
            .clickable { onCheckedChange(!checked) },
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                    IconAndTextPadding()
                }
                Text(title)
            }
            SpacerPadding()
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange(it) }
            )
        }
    }
}