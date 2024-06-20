package me.dizzykitty3.androidtoolkitty.ui.settings

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.QR_CODE_GENERATOR_SCREEN
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui_components.Bold
import me.dizzykitty3.androidtoolkitty.ui_components.Card
import me.dizzykitty3.androidtoolkitty.ui_components.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.ui_components.Screen
import me.dizzykitty3.androidtoolkitty.ui_components.UnderDevelopmentTip
import me.dizzykitty3.androidtoolkitty.ui_components.WarningAlertDialogButton
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.finishApp
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.restartApp
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil
import java.util.Locale

@Composable
fun Debugging(navController: NavHostController) {
    val view = LocalView.current
    var showDialog by remember { mutableStateOf(false) }
    val settingsSharedPref = remember { SettingsSharedPref }
    var uiTesting by remember { mutableStateOf(settingsSharedPref.uiTesting) }

    Screen {
        Card(title = R.string.debugging, icon = Icons.Outlined.Terminal) {
            Text(text = "OS version = Android ${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})")
            Text(text = "Locale =  ${Locale.getDefault()}")

            CustomSwitchRow(text = R.string.ui_testing, checked = uiTesting) {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                uiTesting = it
                settingsSharedPref.uiTesting = it
            }

            if (!settingsSharedPref.showOnlineFeatures) {
                OutlinedButton({
                    settingsSharedPref.showOnlineFeatures = true
                    SnackbarUtil.show(view, R.string.success)
                }) { Text(stringResource(R.string.show_online_features)) }
            }

            OutlinedButton(onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                navController.navigate(PERMISSION_REQUEST_SCREEN)
            }) {
                Text(text = stringResource(id = R.string.go_to_permission_request_screen))
            }

            OutlinedButton(onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                showDialog = true
            }) {
                Text(stringResource(R.string.auto_set_volume))
            }

            OutlinedButton(
                onClick = { navController.navigate(QR_CODE_GENERATOR_SCREEN) }
            ) {
                Text(stringResource(R.string.qr_code_generator))
            }

            OutlinedButton(onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                view.context.restartApp()
            }) {
                Text(text = stringResource(id = R.string.restart_app))
            }

            WarningAlertDialogButton(
                buttonText = stringResource(R.string.erase_all_app_data),
                dialogMessageTitle = stringResource(R.string.warning),
                dialogMessage = {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.warning_erase_all_data_1))
                            append(" ")
                            Bold(R.string.warning_erase_all_data_2)
                            append("\n\n")
                            append(stringResource(R.string.warning_erase_all_data_3))
                        }
                    )
                },
                positiveButtonText = stringResource(R.string.erase_all_data),
                negativeButtonText = null,
                onClickAction = {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    settingsSharedPref.eraseAllData()
                    view.context.finishApp()
                }
            )

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    icon = {
                        Icon(imageVector = Icons.Outlined.WbSunny, contentDescription = null)
                    },
                    title = { Text(stringResource(R.string.auto_set_volume)) },
                    text = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            UnderDevelopmentTip()
                            Row {
                                Column(modifier = Modifier.weight(0.5f)) {
                                    Text(text = "8:00 AM - 5:59 PM")
                                    Text(text = "6:00 PM - 10:59 PM")
                                    Text(text = "11:00 PM - 5:59 AM")
                                    Text(text = "6:00 PM - 7:59 AM")
                                }
                                Column(modifier = Modifier.weight(0.5f)) {
                                    Text(text = "mute")
                                    Text(text = "40%/60%")
                                    Text(text = "25%")
                                    Text(text = "40%/60%")
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Row {
                            Button(onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                showDialog = false

                                if (PermissionUtil.noBluetoothPermission(view.context)) {
                                    navController.navigate(PERMISSION_REQUEST_SCREEN)
                                    return@Button
                                }

                                SettingsSharedPref.autoSetMediaVolume = 40
                            }) {
                                Text(text = "40%")
                            }
                        }
                        Row {
                            Button(onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                showDialog = false

                                if (PermissionUtil.noBluetoothPermission(view.context)) {
                                    navController.navigate(PERMISSION_REQUEST_SCREEN)
                                    return@Button
                                }

                                SettingsSharedPref.autoSetMediaVolume = 60
                            }) {
                                Text(text = "60%")
                            }
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                            showDialog = false
                            SettingsSharedPref.autoSetMediaVolume = -1
                        }) {
                            Text(text = stringResource(id = R.string.turn_off))
                        }
                    }
                )
            }
        }
    }
}