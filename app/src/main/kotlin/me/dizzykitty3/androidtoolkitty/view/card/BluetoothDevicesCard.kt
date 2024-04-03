package me.dizzykitty3.androidtoolkitty.view.card

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
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
import me.dizzykitty3.androidtoolkitty.foundation.context_service.ToastService
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomCardNoIcon

@Composable
fun BluetoothDevicesCard() {
    CustomCardNoIcon(title = R.string.bluetooth_devices) {
        val context = LocalContext.current

        val permission = Manifest.permission.BLUETOOTH_CONNECT

        var showText by remember { mutableStateOf(false) }
        var showName by remember { mutableStateOf(false) }

        var bluetoothAdapter by remember { mutableStateOf<BluetoothAdapter?>(null) }
        var pairedDevices by remember { mutableStateOf<Set<BluetoothDevice>>(emptySet()) }

        Button(onClick = {
            // Check permission
            if (ActivityCompat.checkSelfPermission(context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), 1)
                return@Button
            }

            // Get system service
            val bluetoothManager =
                context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothAdapter = bluetoothManager.adapter

            // Show current device name and paired devices' name and MAC address
            if (bluetoothAdapter!!.isEnabled) {
                pairedDevices = bluetoothAdapter!!.bondedDevices
                if (!showName) showName = true
                if (!showText) showText = true
                return@Button
            }

            // When Bluetooth is OFF
            ToastService(context).toast(context.getString(R.string.bluetooth_disabled))
        }) {
            Text(text = stringResource(id = R.string.show_paired_devices))
        }

        if (showName)
            Text(text = "${stringResource(id = R.string.current_device)}\n${bluetoothAdapter?.name}\n")

        if (showText) {
            Text(text = stringResource(id = R.string.paired_devices))
            pairedDevices.forEach { device ->
                val deviceInfo = "${device.name} (${device.address})\n"
                Text(text = deviceInfo)
            }
        }
    }
}