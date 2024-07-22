package me.dizzykitty3.androidtoolkitty.home

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bluetooth
import androidx.compose.material.icons.outlined.BluetoothConnected
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_PERMISSION_REQUEST
import me.dizzykitty3.androidtoolkitty.S_ENABLE_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomIconPopup
import me.dizzykitty3.androidtoolkitty.uicomponents.PrimaryColorText
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.BluetoothUtil
import me.dizzykitty3.androidtoolkitty.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openSystemSettings
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDevice(navController: NavHostController) {
    Card(R.string.bluetooth_devices, Icons.Outlined.Bluetooth) {
        val view = LocalView.current
        var showResult by remember { mutableStateOf(false) }
        var bluetoothAdapter by remember { mutableStateOf<BluetoothAdapter?>(null) }
        var pairedDevices by remember { mutableStateOf<List<BluetoothDevice>>(emptyList()) }
        var size by remember { mutableIntStateOf(0) }

        OutlinedButton(
            onClick = {
                view.hapticFeedback()
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
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.BluetoothConnected,
                contentDescription = stringResource(id = R.string.show_paired_devices),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SpacerPadding()
            Text(
                text = if (showResult) stringResource(id = R.string.refresh)
                else stringResource(id = R.string.show_paired_devices)
            )
        }

        if (showResult) {
            Text(text = "${stringResource(id = R.string.current_device)} ${bluetoothAdapter?.name}\n")

            if (size == 0) Text(text = stringResource(id = R.string.no_paired_devices))
            else Text(text = stringResource(id = R.string.paired_devices))

            pairedDevices.forEach { device ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = device.name ?: stringResource(id = R.string.unknown_device))
                    SpacerPadding()
                    CustomIconPopup(type(device.type), device.address)
                }
            }

            BluetoothDeviceTypeDialog()
        }
    }
}

@Composable
private fun BluetoothDeviceTypeDialog() {
    val view = LocalView.current
    var showDialog by remember { mutableStateOf(false) }

    TextButton(onClick = {
        view.hapticFeedback()
        showDialog = true
    }) {
        Text(text = stringResource(id = R.string.what_is_bt_ble_and_dual))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = buildAnnotatedString { PrimaryColorText(id = R.string.bluetooth_devices_types_1) })
                    Text(text = stringResource(id = R.string.bluetooth_devices_types_2))
                    Text(text = buildAnnotatedString { PrimaryColorText(id = R.string.bluetooth_devices_types_3) })
                    Text(text = stringResource(id = R.string.bluetooth_devices_types_4))
                    Text(text = buildAnnotatedString { PrimaryColorText(id = R.string.bluetooth_devices_types_5) })
                    Text(text = stringResource(id = R.string.bluetooth_devices_types_6))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        view.hapticFeedback()
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    elevation = ButtonDefaults.buttonElevation(1.dp)
                ) {
                    Text(
                        text = stringResource(android.R.string.ok),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
    }
}

@Composable
private fun type(type: Int): String =
    when (type) {
        1 -> stringResource(id = R.string.bt)
        2 -> stringResource(id = R.string.ble)
        3 -> stringResource(id = R.string.dual)
        else -> stringResource(id = R.string.unknown)
    }