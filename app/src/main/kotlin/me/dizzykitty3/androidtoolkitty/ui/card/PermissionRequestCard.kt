package me.dizzykitty3.androidtoolkitty.ui.card

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.utils.OsVersion
import me.dizzykitty3.androidtoolkitty.foundation.utils.TIntent

@SuppressLint("InlinedApi")
private const val BT_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
private const val BT = Manifest.permission.BLUETOOTH
private const val BT_ADMIN = Manifest.permission.BLUETOOTH_ADMIN

@Composable
fun PermissionRequestCard() {
    CustomCard(
        title = (R.string.permission_request),
        icon = Icons.Outlined.Shield
    ) {
        val context = LocalContext.current

        if (OsVersion.android12()) Text(text = stringResource(id = R.string.bluetooth_connect))
        else Text(text = stringResource(id = R.string.bluetooth_bluetooth_admin))
        Button(
            onClick = { requestPermission(context) }
        ) {
            Text(text = stringResource(id = R.string.permission_request))
        }

        CustomGroupDivider()
        ManuallyGrant()
    }
}


@Composable
fun ManuallyGrant() {
    Text(text = stringResource(id = R.string.missed_sys_popup))
    Button(
        onClick = { TIntent.openPermissionPage() }
    ) {
        Text(text = stringResource(id = R.string.manually_grant))
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