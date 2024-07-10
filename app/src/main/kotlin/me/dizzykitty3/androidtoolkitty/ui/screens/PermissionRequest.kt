package me.dizzykitty3.androidtoolkitty.ui.screens

import android.view.HapticFeedbackConstants
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.domain.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.domain.utils.PermissionUtil.noBluetoothPermission
import me.dizzykitty3.androidtoolkitty.domain.utils.PermissionUtil.noLocationPermission
import me.dizzykitty3.androidtoolkitty.domain.utils.PermissionUtil.requestBluetoothPermission
import me.dizzykitty3.androidtoolkitty.domain.utils.PermissionUtil.requestLocationPermission
import me.dizzykitty3.androidtoolkitty.domain.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.components.Screen
import me.dizzykitty3.androidtoolkitty.ui.viewmodel.SettingsViewModel

@Composable
fun PermissionRequest(settingsViewModel: SettingsViewModel) {
    Screen {
        Card(
            title = (R.string.request_permission),
            icon = Icons.Outlined.Shield
        ) {
            var clickCount by remember { mutableIntStateOf(0) }
            var clickCount2 by remember { mutableIntStateOf(0) }
            val view = LocalView.current

            if (OSVersion.a12()) Text(text = stringResource(id = R.string.bluetooth_connect))
            else Text(text = stringResource(id = R.string.bluetooth_bluetooth_admin))

            Button(
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    if (view.context.noBluetoothPermission()) {
                        view.context.requestBluetoothPermission()
                        clickCount++
                        return@Button
                    }
                    view.showSnackbar(R.string.success_and_back)
                }
            ) {
                Text(text = stringResource(id = R.string.request_permission))
            }

            GroupDivider()

            Text(stringResource(R.string.fine_location))

            Button(
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    if (view.context.noLocationPermission()) {
                        view.context.requestLocationPermission()
                        clickCount2++
                        return@Button
                    }
                    view.showSnackbar(R.string.success_and_back)
                }
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

    Text(text = stringResource(id = R.string.missed_sys_popup))
    TextButton(
        onClick = {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            view.context.openAppDetailSettings()
        }
    ) {
        Text(
            text = stringResource(id = R.string.go_to_settings),
            color = MaterialTheme.colorScheme.primary
        )
    }
}