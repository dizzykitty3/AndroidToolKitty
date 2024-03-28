package me.dizzykitty3.androidtoolkitty.view.card

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.MainActivity
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomAlertDialogButton
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCardNoIcon
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomGroupTitleText
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.util.AudioUtils
import me.dizzykitty3.androidtoolkitty.common.util.OsVersion
import me.dizzykitty3.androidtoolkitty.common.util.ToastUtils
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun SettingsCard(navController: NavHostController) {
    val c = LocalContext.current
    CustomCardNoIcon(
        title = c.getString(R.string.settings)
    ) {
        val autoClearClipboard = SettingsViewModel().getIsAutoClearClipboard(c)
        var mAutoClearClipboard by remember { mutableStateOf(autoClearClipboard) }

        val oneHandedMode = SettingsViewModel().getIsOneHandedMode(c)
        var mOneHandedMode by remember { mutableStateOf(oneHandedMode) }

        val dynamicColor = SettingsViewModel().getIsDynamicColor(c)
        var mDynamicColor by remember { mutableStateOf(dynamicColor) }

        var showVolumeDialog by remember { mutableStateOf(false) }

        var showVolumeOptionLabelDialog by remember { mutableStateOf(false) }

        CustomGroupTitleText(c.getString(R.string.feature))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mAutoClearClipboard = !mAutoClearClipboard
                SettingsViewModel().setIsAutoClearClipboard(c, mAutoClearClipboard)
            }
        ) {
            Text(text = c.getString(R.string.clear_clipboard_on_launch))

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = mAutoClearClipboard,
                onCheckedChange = {
                    mAutoClearClipboard = it
                    SettingsViewModel().setIsAutoClearClipboard(c, it)
                }
            )
        }

        Button(onClick = { showVolumeDialog = true }) {
            Text(text = c.getString(R.string.edit_custom_volume_button))
        }

        if (showVolumeDialog) {
            val maxVolume = AudioUtils(c).getMaxVolumeIndex()

            var newCustomVolume by remember { mutableFloatStateOf(0f) }

            AlertDialog(
                onDismissRequest = {
                    showVolumeDialog = false
                },
                title = {
                    Text(text = "${c.getString(R.string.add_custom_volume)}\n${newCustomVolume.toInt()}% -> ${(newCustomVolume * 0.01 * maxVolume).toInt()}/$maxVolume")
                },
                text = {
                    Slider(
                        value = newCustomVolume,
                        onValueChange = {
                            newCustomVolume = it
                        },
                        valueRange = 0f..100f
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if ((newCustomVolume * 0.01 * maxVolume).toInt() == 0 && newCustomVolume.toInt() != 0) {
                                ToastUtils(c).showToast(c.getString(R.string.system_media_volume_levels_limited))
                                return@Button
                            } else {
                                SettingsViewModel().setCustomVolume(c, newCustomVolume.toInt())
                                showVolumeDialog = false
                                AudioUtils(c).setVolume((newCustomVolume * 0.01 * maxVolume).toInt())
                                showVolumeOptionLabelDialog = true
                            }
                        }
                    ) {
                        Text(text = c.getString(android.R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showVolumeDialog = false }
                    ) {
                        Text(text = c.getString(android.R.string.cancel))
                    }
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_dialog))
            )
        }

        if (showVolumeOptionLabelDialog) {
            var optionLabel by remember { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = {
                    showVolumeOptionLabelDialog = false
                },
                title = {
                    Text(text = "You can set a label for it")
                },
                text = {
                    OutlinedTextField(
                        value = optionLabel,
                        onValueChange = { optionLabel = it },
                        label = { Text(text = "Option label") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                SettingsViewModel().setCustomVolumeOptionLabel(
                                    c,
                                    optionLabel
                                )
                            }
                        ),
                        placeholder = { Text(text = "optional") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            SettingsViewModel().setCustomVolumeOptionLabel(c, optionLabel)
                            showVolumeOptionLabelDialog = false
                        }
                    ) {
                        Text(text = c.getString(android.R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showVolumeOptionLabelDialog = false }
                    ) {
                        Text(text = c.getString(android.R.string.cancel))
                    }
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_dialog))
            )
        }

        CustomSpacerPadding()
        HorizontalDivider()
        CustomSpacerPadding()

        CustomGroupTitleText(c.getString(R.string.display))

        if (OsVersion.android12()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    mDynamicColor = !mDynamicColor
                    onClickDynamicColorButton(mDynamicColor, c)
                }
            ) {
                Text(text = c.getString(R.string.material_you_dynamic_color))
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = mDynamicColor,
                    onCheckedChange = {
                        mDynamicColor = it
                        onClickDynamicColorButton(it, c)
                    }
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mOneHandedMode = !mOneHandedMode
                SettingsViewModel().setIsOneHandedMode(c, mOneHandedMode)
            }
        ) {
            Text(text = c.getString(R.string.one_handed_mode))
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mOneHandedMode,
                onCheckedChange = {
                    mOneHandedMode = it
                    SettingsViewModel().setIsOneHandedMode(c, it)
                }
            )
        }

        Button(
            onClick = { navController.navigate("HideCardSettingScreen") }) {
            Text(text = c.getString(R.string.customize_my_home_page))
        }

        CustomSpacerPadding()
        HorizontalDivider()
        CustomSpacerPadding()

        CustomGroupTitleText(c.getString(R.string.debugging))

        CustomAlertDialogButton(
            buttonText = c.getString(R.string.erase_all_app_data),
            dialogMessageTitle = c.getString(R.string.warning),
            dialogMessage = c.getString(R.string.warning_erase_all_data),
            positiveButtonText = c.getString(R.string.erase_all_data),
            negativeButtonText = null,
            onClickAction = {
                SettingsViewModel().clear(c)
                (c as Activity).finish()
            }
        )
    }
}

private fun onClickDynamicColorButton(isDynamicColor: Boolean, c: Context) {
    SettingsViewModel().setIsDynamicColor(c, isDynamicColor)

    val intent = Intent(c, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    c.startActivity(intent)

    (c as Activity).finish()
}