package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noLocationPermission
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.requestBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.requestLocationPermission
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar

@Composable
fun PermissionRequest(settingsViewModel: SettingsViewModel) {
    Screen {
        Card(R.string.request_permission, Icons.Outlined.Shield) {
            var clickCount by remember { mutableIntStateOf(0) }
            var clickCount2 by remember { mutableIntStateOf(0) }
            val view = LocalView.current

            // TODO Split

            Text(stringResource(R.string.bluetooth_connect))

            Button(
                {
                    view.hapticFeedback()
                    if (view.context.noBluetoothPermission()) {
                        view.context.requestBluetoothPermission()
                        clickCount++
                        return@Button
                    }
                    view.showSnackbar(R.string.success_and_back)
                },
                elevation = ButtonDefaults.buttonElevation(1.dp)
            ) { Text(stringResource(R.string.request_permission)) }

            GroupDivider()

            Text(stringResource(R.string.fine_location))

            Button(
                {
                    view.hapticFeedback()
                    if (view.context.noLocationPermission()) {
                        view.context.requestLocationPermission()
                        clickCount2++
                        return@Button
                    }
                    view.showSnackbar(R.string.success_and_back)
                },
                elevation = ButtonDefaults.buttonElevation(1.dp)
            ) { Text(stringResource(R.string.request_permission)) }

            if (clickCount >= 2 || clickCount2 >= 2) {
                GroupDivider()
                ManuallyGrant()
            }
        }
    }
}

@Composable
fun ManuallyGrant() {
    val view = LocalView.current

    Text(stringResource(R.string.missed_sys_popup))
    TextButton({
        view.hapticFeedback()
        view.context.openAppDetailSettings()
    }) {
        Text(
            stringResource(R.string.go_to_settings),
            color = MaterialTheme.colorScheme.primary
        )
    }
}