package me.dizzykitty3.androidtoolkitty.ui.permission_request

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
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui_components.Card
import me.dizzykitty3.androidtoolkitty.ui_components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui_components.Screen
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil

@Preview
@Composable
fun PermissionRequest() {
    Screen {
        Card(
            title = (R.string.request_permission),
            icon = Icons.Outlined.Shield
        ) {
            var clickCount by remember { mutableIntStateOf(0) }
            val view = LocalView.current

            if (OSVersion.a12()) Text(text = stringResource(id = R.string.bluetooth_connect))
            else Text(text = stringResource(id = R.string.bluetooth_bluetooth_admin))

            Button(
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    if (PermissionUtil.noBluetoothPermission(view.context)) {
                        PermissionUtil.requestBluetoothPermission(view.context)
                        clickCount++
                        return@Button
                    }
                    SnackbarUtil.show(view, R.string.success_and_back)
                }
            ) {
                Text(text = stringResource(id = R.string.request_permission))
            }

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