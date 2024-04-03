package me.dizzykitty3.androidtoolkitty.view.card

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

        var bluetoothAdapter by remember { mutableStateOf<BluetoothAdapter?>(null) }
        var pairedDevices by remember { mutableStateOf<Set<BluetoothDevice>>(emptySet()) }

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
            Text(text = stringResource(id = R.string.paired_devices))
            pairedDevices.forEach { device ->
                val deviceInfo = "${device.name} (${device.address})\n"
                Text(text = deviceInfo)
            }
        }
    }
}

private fun noPermission(context: Context): Boolean {
    return if (OsVersion.android12())
        check(context, BLE_CONNECT)
    else
        check(context, BLE) || check(context, BLE_ADMIN)
}

private fun check(context: Context, permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(context, permission) != GRANTED
}

private fun requestPermission(context: Context) {
    if (OsVersion.android12()) {
        request(context, arrayOf(BLE_CONNECT))
        return
    }
    request(context, arrayOf(BLE, BLE_ADMIN))
}

private fun request(context: Context, permission: Array<String>) {
    ActivityCompat.requestPermissions(context as Activity, permission, 1)
}

@SuppressLint("InlinedApi")
private const val BLE_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
private const val BLE = Manifest.permission.BLUETOOTH
private const val BLE_ADMIN = Manifest.permission.BLUETOOTH_ADMIN
private const val GRANTED = PackageManager.PERMISSION_GRANTED