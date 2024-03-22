package me.dizzykitty3.androidtoolkitty.view.card

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.MainActivity
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomAlertDialogButton
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomNoIconCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel


@Composable
fun SettingsCard() {
    val c = LocalContext.current
    CustomNoIconCard(
        title = c.getString(R.string.settings)
    ) {
        val mIsAutoClearClipboard = SettingsViewModel().getIsAutoClearClipboard(c)
        var isAutoClearClipboard by remember { mutableStateOf(mIsAutoClearClipboard) }
        val mIsOneHandedMode = SettingsViewModel().getIsOneHandedMode(c)
        var isOneHandedMode by remember { mutableStateOf(mIsOneHandedMode) }
        val isDynamicColor = SettingsViewModel().getIsDynamicColor(c)
        var mIsDynamicColor by remember { mutableStateOf(isDynamicColor) }
        Text(
            text = c.getString(R.string.feature),
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
                mIsDynamicColor = !mIsDynamicColor
                SettingsViewModel().setIsDynamicColor(c, mIsDynamicColor)
            }
        ) {
            Text(
                text = "Material You dynamic color"
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsDynamicColor,
                onCheckedChange = {
                    mIsDynamicColor = it
                    SettingsViewModel().setIsDynamicColor(c, it)
                    val intent = Intent(c, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    c.startActivity(intent)
                    (c as Activity).finish()
                }
            )
        }
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
        CustomSpacerPadding()
        HorizontalDivider()
        CustomSpacerPadding()
        Text(
            text = c.getString(R.string.debugging),
            style = MaterialTheme.typography.titleMedium
        )
        CustomSpacerPadding()
        CustomAlertDialogButton(
            buttonText = c.getString(R.string.erase_all_app_data),
            dialogMessageTitle = c.getString(R.string.warning),
            dialogMessage = c.getString(R.string.warning_erase_all_data),
            positiveButtonText = c.getString(R.string.clear_all_data),
            negativeButtonText = null,
            onClickAction = {
                SettingsViewModel().clear(c)
                (c as Activity).finish()
            }
        )
    }
}