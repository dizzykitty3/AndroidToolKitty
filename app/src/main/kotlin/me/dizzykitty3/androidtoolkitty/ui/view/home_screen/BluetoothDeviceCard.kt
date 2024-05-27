package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomIconPopup
import me.dizzykitty3.androidtoolkitty.ui.component.PrimaryColor
import me.dizzykitty3.androidtoolkitty.ui.component.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.BluetoothUtil
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil
import me.dizzykitty3.androidtoolkitty.utils.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil
import me.dizzykitty3.androidtoolkitty.utils.SETTING_ENABLE_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil

@Preview
@Composable
private fun BluetoothDeviceCardPreview() {
    val navController = rememberNavController()
    BluetoothDeviceCard(navController)
}

@Composable
fun BluetoothDeviceCard(navController: NavHostController) {
    CustomCard(
        icon = Icons.Outlined.Bluetooth,
        titleRes = R.string.bluetooth_devices
    ) {
        val view = LocalView.current
        val context = LocalContext.current
        var showResult by remember { mutableStateOf(false) }
        var bluetoothAdapter by remember { mutableStateOf<BluetoothAdapter?>(null) }
        var pairedDevices by remember { mutableStateOf<List<BluetoothDevice>>(emptyList()) }
        var size by remember { mutableIntStateOf(0) }
        val materialColor = MaterialTheme.colorScheme.primary.toArgb()
        val showSnackbarToConfirm = SettingsSharedPref.showSnackbar

        OutlinedButton(
            onClick = {
                // Check permission
                if (PermissionUtil.noBluetoothPermission(context)) {
                    navController.navigate(PERMISSION_REQUEST_SCREEN)
                    return@OutlinedButton
                }

                // Get system service
                bluetoothAdapter = BluetoothUtil.bluetoothAdapter()

                // Show current device name, paired devices' name and MAC address
                if (bluetoothAdapter!!.isEnabled) {
                    @SuppressLint("MissingPermission")
                    pairedDevices = bluetoothAdapter!!.bondedDevices.sortedBy { it.name }
                    size = pairedDevices.size
                    showResult = true
                    return@OutlinedButton
                }

                // When Bluetooth is OFF
                if (showSnackbarToConfirm) {
                    SnackbarUtil.snackbar(
                        view,
                        messageRes = R.string.bluetooth_disabled,
                        buttonTextRes = R.string.turn_on_bluetooth,
                        buttonColor = materialColor,
                        buttonClickListener = {
                            IntentUtil.openSystemSettings(SETTING_ENABLE_BLUETOOTH, context)
                        }
                    )
                } else {
                    IntentUtil.openSystemSettings(SETTING_ENABLE_BLUETOOTH, context)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.BluetoothConnected,
                contentDescription = stringResource(id = R.string.show_paired_devices),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SpacerPadding()
            Text(text = stringResource(id = R.string.show_paired_devices))
        }

        @SuppressLint("MissingPermission")
        if (showResult) {
            Text(text = "${stringResource(id = R.string.current_device)} ${bluetoothAdapter?.name}\n")

            if (size == 0) {
                Text(text = stringResource(id = R.string.no_paired_devices))
            } else {
                Text(text = stringResource(id = R.string.paired_devices))

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
}

@Composable
private fun BluetoothDeviceTypeDialog() {
    var showDialog by remember { mutableStateOf(false) }

    TextButton(onClick = { showDialog = true }) {
        Text(text = stringResource(id = R.string.what_is_bt_ble_and_dual))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = buildAnnotatedString { PrimaryColor(id = R.string.bluetooth_devices_types_1) })
                    Text(text = stringResource(id = R.string.bluetooth_devices_types_2))
                    Text(text = buildAnnotatedString { PrimaryColor(id = R.string.bluetooth_devices_types_3) })
                    Text(text = stringResource(id = R.string.bluetooth_devices_types_4))
                    Text(text = buildAnnotatedString { PrimaryColor(id = R.string.bluetooth_devices_types_5) })
                    Text(text = stringResource(id = R.string.bluetooth_devices_types_6))
                }
            },
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
}

@Composable
private fun type(type: Int): String =
    when (type) {
        1 -> stringResource(id = R.string.bt)
        2 -> stringResource(id = R.string.ble)
        3 -> stringResource(id = R.string.dual)
        else -> stringResource(id = R.string.unknown)
    }