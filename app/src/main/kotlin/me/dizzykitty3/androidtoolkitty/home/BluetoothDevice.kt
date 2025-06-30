package me.dizzykitty3.androidtoolkitty.home

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bluetooth
import androidx.compose.material.icons.outlined.BluetoothConnected
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_PERMISSION_REQUEST
import me.dizzykitty3.androidtoolkitty.S_ENABLE_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomIconPopup
import me.dizzykitty3.androidtoolkitty.uicomponents.ScrollableText
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.BluetoothUtil
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openSystemSettings
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDevice(navController: NavHostController) {
    Card(title = R.string.bluetooth_devices, icon = Icons.Outlined.Bluetooth) {
        val view = LocalView.current
        val haptic = LocalHapticFeedback.current
        var showResult by remember { mutableStateOf(false) }
        var bluetoothAdapter by remember { mutableStateOf<BluetoothAdapter?>(null) }
        var pairedDevices by remember { mutableStateOf<List<BluetoothDevice>>(emptyList()) }
        var size by remember { mutableIntStateOf(0) }

        OutlinedButton(onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)

            // Check permission
            if (view.context.noBluetoothPermission()) {
                navController.navigate(SCR_PERMISSION_REQUEST)
                return@OutlinedButton
            }

            // Get system service
            bluetoothAdapter = BluetoothUtil.bluetoothAdapter
            if (bluetoothAdapter == null) {
                view.showSnackbar(R.string.no_bluetooth_adapter_available)
                return@OutlinedButton
            }

            // Show current device name, paired devices' name and MAC address
            if (bluetoothAdapter!!.isEnabled) {
                pairedDevices = bluetoothAdapter!!.bondedDevices.sortedBy { it.name }
                size = pairedDevices.size
                showResult = true
                return@OutlinedButton
            }

            // When Bluetooth is OFF
            view.context.openSystemSettings(S_ENABLE_BLUETOOTH)
        }) {
            Icon(
                imageVector = Icons.Outlined.BluetoothConnected,
                contentDescription = stringResource(R.string.show_paired_devices),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SpacerPadding()
            Text(stringResource(if (showResult) R.string.refresh else R.string.show_paired_devices))
        }

        if (showResult) {
            Text(stringResource(R.string.current_device))
            ScrollableText("${bluetoothAdapter?.name}\n")

            if (size == 0) {
                Text(stringResource(R.string.no_paired_devices))
            } else {
                Text(stringResource(R.string.paired_devices))

                pairedDevices.forEach { device ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ScrollableText(device.name ?: stringResource(R.string.unknown_device))
                        SpacerPadding()
                        CustomIconPopup(device.type.toTypeName(), device.address)
                    }
                }
            }
        }
    }
}

private fun Int.toTypeName(): String =
    when (this) {
        1 -> "BT"
        2 -> "BLE"
        3 -> "Dual"
        else -> "Unknown"
    }