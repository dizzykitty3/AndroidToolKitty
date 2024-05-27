package me.dizzykitty3.androidtoolkitty.ui.view.permission_request_screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.GroupDivider
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil

@Preview
@Composable
fun PermissionRequestCard() {
    CustomCard(
        titleRes = (R.string.request_permission),
        icon = Icons.Outlined.Shield
    ) {
        var clickCount by remember { mutableIntStateOf(0) }
        val view = LocalView.current
        val context = LocalContext.current

        if (OSVersion.android12()) Text(text = stringResource(id = R.string.bluetooth_connect))
        else Text(text = stringResource(id = R.string.bluetooth_bluetooth_admin))

        Button(
            onClick = {
                if (PermissionUtil.noBluetoothPermission(context)) {
                    PermissionUtil.requestBluetoothPermission(context)
                    clickCount++
                    return@Button
                }
                SnackbarUtil.snackbar(view, R.string.success_and_back)
            }
        ) {
            Text(text = stringResource(id = R.string.request_permission))
        }

        if (clickCount >= 2) {
            GroupDivider()
            ManuallyGrant()
        }
    }
}

@Composable
fun ManuallyGrant() {
    val context = LocalContext.current

    Text(text = stringResource(id = R.string.missed_sys_popup))
    TextButton(
        onClick = { IntentUtil.openAppDetailSettings(context) }
    ) {
        Text(
            text = stringResource(id = R.string.go_to_settings),
            color = MaterialTheme.colorScheme.primary
        )
    }
}