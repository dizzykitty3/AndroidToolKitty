package me.dizzykitty3.androidtoolkitty.view.card

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.context_service.BluetoothService
import me.dizzykitty3.androidtoolkitty.foundation.context_service.ToastService
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomCardNoIcon
import me.dizzykitty3.androidtoolkitty.foundation.utils.OsVersion

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDevicesCard() {
    CustomCardNoIcon(title = R.string.bluetooth_devices) {
        val context = LocalContext.current

        var showResult by remember { mutableStateOf(false) }
        var showDialog by remember { mutableStateOf(false) }

        var bluetoothAdapter by remember { mutableStateOf<BluetoothAdapter?>(null) }
        var pairedDevices by remember { mutableStateOf<Set<BluetoothDevice>>(emptySet()) }

        var size by remember { mutableIntStateOf(0) }

        Button(
            onClick = {
                // Check permission
                if (noPermission(context)) {
                    ToastService(context).toast(context.getString(R.string.permission_not_granted))
                    requestPermission(context)
                    return@Button
                }

                // Get system service
                bluetoothAdapter = BluetoothService(context).bluetoothAdapter()

                // Show current device name and paired devices' name and MAC address
                if (bluetoothAdapter!!.isEnabled) {
                    pairedDevices = bluetoothAdapter!!.bondedDevices
                    size = pairedDevices.size
                    showResult = true
                    return@Button
                }

                // When Bluetooth is OFF
                ToastService(context).toast(context.getString(R.string.bluetooth_disabled))
            }
        ) {
            Text(text = stringResource(id = R.string.show_paired_devices))
        }

        if (showResult) {
            Text(text = "${stringResource(id = R.string.current_device)}\n${bluetoothAdapter?.name}\n")
            if (size > 0) {
                Text(text = stringResource(id = R.string.paired_devices))
                pairedDevices.forEach { device ->
                    val deviceInfo = "${device.name} (${type(device.type)})\n${device.address}\n"
                    Text(text = deviceInfo)
                }
                TextButton(
                    onClick = { showDialog = true }
                ) {
                    Text(text = stringResource(id = R.string.what_is_bt_ble_and_dual))
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        text = { Text(text = stringResource(id = R.string.bluetooth_devices_types)) },
                        confirmButton = {
                            Button(
                                onClick = { showDialog = false },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = stringResource(android.R.string.ok),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    )
                }
            } else {
                Text(text = stringResource(id = R.string.no_paired_devices))
            }
        }
    }
}

@Composable
private fun type(type: Int): String {
    return when (type) {
        1 -> stringResource(id = R.string.bt)
        2 -> stringResource(id = R.string.ble)
        3 -> stringResource(id = R.string.dual)
        else -> stringResource(id = R.string.unknown)
    }
}

private fun noPermission(context: Context): Boolean {
    return if (OsVersion.android12())
        check(context, BT_CONNECT)
    else
        check(context, BT) || check(context, BT_ADMIN)
}

private fun check(context: Context, permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(context, permission) != GRANTED
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

@SuppressLint("InlinedApi")
private const val BT_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
private const val BT = Manifest.permission.BLUETOOTH
private const val BT_ADMIN = Manifest.permission.BLUETOOTH_ADMIN
private const val GRANTED = PackageManager.PERMISSION_GRANTED