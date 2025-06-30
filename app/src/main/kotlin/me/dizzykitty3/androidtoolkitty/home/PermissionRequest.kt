package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.RowDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.requestBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar

@Composable
fun PermissionRequest(navController: NavHostController) {
    Screen(navController) {
        Card(title = R.string.request_permission, icon = Icons.Outlined.Shield) {
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
                    view.showSnackbar(R.string.granted)
                    navController.popBackStack()
                },
                elevation = ButtonDefaults.buttonElevation(1.dp)
            ) { Text(stringResource(R.string.request_permission)) }

            if (clickCount >= 2) {
                RowDivider()
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