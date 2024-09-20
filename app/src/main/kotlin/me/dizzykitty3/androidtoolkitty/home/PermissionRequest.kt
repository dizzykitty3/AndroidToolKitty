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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.requestBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar

@Composable
fun PermissionRequest() {
    Screen {
        Card(R.string.request_permission, Icons.Outlined.Shield) {
            var clickCount by remember { mutableIntStateOf(0) }
            val view = LocalView.current
            val haptic = LocalHapticFeedback.current

            Text(stringResource(R.string.bluetooth_connect))

            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    if (view.context.noBluetoothPermission()) {
                        view.context.requestBluetoothPermission()
                        clickCount++
                        return@Button
                    }
                    view.showSnackbar(R.string.success_and_back)
                },
                elevation = ButtonDefaults.buttonElevation(1.dp)
            ) { Text(stringResource(R.string.request_permission)) }

            if (clickCount >= 2) {
                GroupDivider()
                ManuallyGrant()
            }
        }
    }
}

@Composable
fun ManuallyGrant() {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current

    Text(stringResource(R.string.missed_sys_popup))
    TextButton({
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        view.context.openAppDetailSettings()
    }) {
        Text(
            stringResource(R.string.go_to_settings),
            color = MaterialTheme.colorScheme.primary
        )
    }
}