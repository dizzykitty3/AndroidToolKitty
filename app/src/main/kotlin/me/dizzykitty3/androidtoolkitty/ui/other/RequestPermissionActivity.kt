package me.dizzykitty3.androidtoolkitty.ui.other

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.theme.AppTheme
import me.dizzykitty3.androidtoolkitty.uicomponents.BaseCard
import me.dizzykitty3.androidtoolkitty.uicomponents.RowDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.requestBluetoothPermission

@AndroidEntryPoint
class RequestPermissionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ) { innerPadding ->
                    Box(
                        Modifier.padding(
                            start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                            end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                        )
                    ) {
                        Screen(screenTitle = R.string.request_permission) {
                            RequestPermissionComposable()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RequestPermissionComposable() {
    BaseCard(title = R.string.request_permission, icon = Icons.Outlined.Shield) {
        var clickCount by remember { mutableIntStateOf(0) }
        val view = LocalView.current
        val activity = LocalActivity.current
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
                activity?.finish()
            }
        ) { Text(stringResource(R.string.request_permission)) }

        if (clickCount >= 2) {
            RowDivider()
            ManuallyGrant()
        }
    }
}

@Composable
private fun ManuallyGrant() {
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
