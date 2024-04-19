package me.dizzykitty3.androidtoolkitty.ui.card

import android.app.Activity
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.const.BT
import me.dizzykitty3.androidtoolkitty.foundation.const.BT_ADMIN
import me.dizzykitty3.androidtoolkitty.foundation.const.BT_CONNECT
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.util.IntentUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.OsVersion

@Composable
fun PermissionRequestCard() {
    CustomCard(
        title = (R.string.permission_request),
        icon = Icons.Outlined.Shield
    ) {
        BluetoothPermission()
        CustomGroupDivider()
        ManuallyGrant()
    }
}

@Composable
fun BluetoothPermission() {
    val context = LocalContext.current

    if (OsVersion.android12()) Text(text = stringResource(id = R.string.bluetooth_connect))
    else Text(text = stringResource(id = R.string.bluetooth_bluetooth_admin))

    Button(
        onClick = { requestPermission(context) }
    ) {
        Text(text = stringResource(id = R.string.permission_request))
    }
}

@Composable
fun ManuallyGrant() {
    Text(text = stringResource(id = R.string.missed_sys_popup))
    TextButton(
        onClick = { IntentUtil.openPermissionPage() }
    ) {
        Text(
            text = stringResource(id = R.string.manually_grant),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

private fun requestPermission(context: Context) {
    if (OsVersion.android12()) {
        request(context, arrayOf(BT_CONNECT))
        return
    }
    request(context, arrayOf(BT, BT_ADMIN))
}

private fun request(context: Context, permission: Array<String>) {
    ActivityCompat.requestPermissions(context as Activity, permission, 1)
}