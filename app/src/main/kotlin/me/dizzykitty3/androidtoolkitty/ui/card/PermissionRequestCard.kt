package me.dizzykitty3.androidtoolkitty.ui.card

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
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.util.IntentUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.OsVersion
import me.dizzykitty3.androidtoolkitty.foundation.util.PermissionUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.SnackbarUtil

@Composable
fun PermissionRequestCard() {
    CustomCard(
        title = (R.string.request_permission),
        icon = Icons.Outlined.Shield
    ) {
        var clickCount by remember { mutableIntStateOf(0) }

        val context = LocalContext.current

        if (OsVersion.android12()) Text(text = stringResource(id = R.string.bluetooth_connect))
        else Text(text = stringResource(id = R.string.bluetooth_bluetooth_admin))

        Button(
            onClick = {
                if (PermissionUtil.noBluetoothPermission(context)) {
                    PermissionUtil.requestBluetoothPermission(context)
                    clickCount++
                    return@Button
                }
                SnackbarUtil.snackbar(R.string.success_and_back)
            }
        ) {
            Text(text = stringResource(id = R.string.request_permission))
        }

        if (clickCount >= 2) {
            CustomGroupDivider()
            ManuallyGrant()
        }
    }
}

@Composable
fun ManuallyGrant() {
    val context = LocalContext.current

    Text(text = stringResource(id = R.string.missed_sys_popup))
    TextButton(
        onClick = { IntentUtil.openPermissionPage(context) }
    ) {
        Text(
            text = stringResource(id = R.string.go_to_settings),
            color = MaterialTheme.colorScheme.primary
        )
    }
}