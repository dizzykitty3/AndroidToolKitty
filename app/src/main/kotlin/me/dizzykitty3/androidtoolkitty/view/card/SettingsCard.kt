package me.dizzykitty3.androidtoolkitty.view.card

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.MainActivity
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomAlertDialogButton
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomBoldText
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCardNoIcon
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomGroupTitleText
import me.dizzykitty3.androidtoolkitty.common.util.OsVersion
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

        val volumeSlideSteps = SettingsViewModel().getIsSliderIncrementFivePercent(c)
        var mVolumeSlideSteps by remember { mutableStateOf(volumeSlideSteps) }

        CustomGroupTitleText(c.getString(R.string.general))

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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mVolumeSlideSteps = !mVolumeSlideSteps
                SettingsViewModel().setIsSliderIncrementFivePercent(c, mVolumeSlideSteps)
            }
        ) {
            Text(text = c.getString(R.string.set_slider_increment_5))

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = mVolumeSlideSteps,
                onCheckedChange = {
                    mVolumeSlideSteps = it
                    SettingsViewModel().setIsSliderIncrementFivePercent(c, it)
                }
            )
        }


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

        CustomGroupDivider()

        CustomGroupTitleText(c.getString(R.string.customize))

        Button(
            onClick = { navController.navigate("HideCardSettingScreen") }) {
            Text(text = c.getString(R.string.customize_my_home_page))
        }

        CustomGroupDivider()

        CustomGroupTitleText(c.getString(R.string.debugging))

        CustomAlertDialogButton(
            buttonText = c.getString(R.string.erase_all_app_data),
            dialogMessageTitle = c.getString(R.string.warning),
            dialogMessage = {
                Text(
                    text = buildAnnotatedString {
                        append(c.getString(R.string.warning_erase_all_data_1))
                        append(" ")
                        CustomBoldText(c.getString(R.string.warning_erase_all_data_2))
                        append("\n\n")
                        append(c.getString(R.string.warning_erase_all_data_3))
                    }
                )
            },
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