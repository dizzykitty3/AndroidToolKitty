package me.dizzykitty3.androidtoolkitty.view.card

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomStaticCard
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun SettingsCard(navController: NavHostController) {
    val c = LocalContext.current
    CustomStaticCard(
        title = c.getString(R.string.settings)
    ) {
        val mIsAutoClearClipboard = SettingsViewModel().getIsAutoClearClipboard(c)
        var isAutoClearClipboard by remember { mutableStateOf(mIsAutoClearClipboard) }
        val mIsOneHandedMode = SettingsViewModel().getIsOneHandedMode(c)
        var isOneHandedMode by remember { mutableStateOf(mIsOneHandedMode) }
        Text(
            text = c.getString(R.string.function),
            style = MaterialTheme.typography.titleMedium
        )
        CustomSpacerPadding()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isAutoClearClipboard = !isAutoClearClipboard
                SettingsViewModel().setIsAutoClearClipboard(c, isAutoClearClipboard)
            }
        ) {
            Text(
                text = c.getString(R.string.clear_clipboard_on_launch)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isAutoClearClipboard,
                onCheckedChange = {
                    isAutoClearClipboard = it
                    SettingsViewModel().setIsAutoClearClipboard(c, it)
                }
            )
        }
        CustomSpacerPadding()
        HorizontalDivider()
        CustomSpacerPadding()
        Text(
            text = c.getString(R.string.display),
            style = MaterialTheme.typography.titleMedium
        )
        CustomSpacerPadding()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isOneHandedMode = !isOneHandedMode
                SettingsViewModel().setIsOneHandedMode(c, isOneHandedMode)
            }
        ) {
            Text(
                text = c.getString(R.string.one_handed_mode)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isOneHandedMode,
                onCheckedChange = {
                    isOneHandedMode = it
                    SettingsViewModel().setIsOneHandedMode(c, it)
                }
            )
        }
        Button(
            onClick = {
                onClickAllCardsButton(c, false)
            }
        ) {
            Text(
                text = c.getString(R.string.collapse_all_cards)
            )
        }
        Button(
            onClick = {
                onClickAllCardsButton(c, true)
            }
        ) {
            Text(
                text = c.getString(R.string.expand_all_cards)
            )
        }
        Button(
            onClick = {
                navController.navigate("HideCardSettingScreen")
            }
        ) {
            Text(
                text = "Hide cards you don't need"
            )
        }
        CustomSpacerPadding()
        HorizontalDivider()
        CustomSpacerPadding()
        Text(
            text = c.getString(R.string.debugging),
            style = MaterialTheme.typography.titleMedium
        )
        CustomSpacerPadding()
        var showDialog by remember { mutableStateOf(false) }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            onClick = {
                showDialog = true
            }
        ) {
            Text(
                text = c.getString(R.string.erase_all_app_data),
                color = MaterialTheme.colorScheme.onError
            )
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Warning") },
                text = { Text("This action will erase all your data, including histories, settings.\nAre you sure you want to proceed?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            SettingsViewModel().clear(c)
                            (c as Activity).finish()
                        }
                    ) {
                        Text(c.getString(android.R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(c.getString(android.R.string.cancel))
                    }
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

private fun onClickAllCardsButton(c: Context, isExpand: Boolean) {
    SettingsViewModel().saveCardExpandedState(c, "card_year_progress", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_volume", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_clipboard", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_url", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_social_media_profile", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_android_system_settings", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_unicode", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_google_maps", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_open_app_on_google_play", isExpand)
}