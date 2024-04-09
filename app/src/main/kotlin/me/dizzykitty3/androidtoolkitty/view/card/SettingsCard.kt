package me.dizzykitty3.androidtoolkitty.view.card

import android.app.Activity
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomAlertDialogButton
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomBoldText
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomGroupTitleText
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomIconAndTextPadding
import me.dizzykitty3.androidtoolkitty.foundation.utils.OsVersion
import me.dizzykitty3.androidtoolkitty.foundation.utils.TIntent
import me.dizzykitty3.androidtoolkitty.foundation.utils.TSnackbar
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

private const val EDIT_HOME_PAGE_SCREEN = "EditHomePageScreen"

@Composable
fun SettingsCard(navController: NavHostController) {
    CustomCard(
        title = R.string.settings
    ) {
        val view = LocalView.current

        val settingsViewModel = remember { SettingsViewModel }

        val autoClearClipboard = settingsViewModel.getIsAutoClearClipboard()
        var mAutoClearClipboard by remember { mutableStateOf(autoClearClipboard) }

        val oneHandedMode = settingsViewModel.getIsOneHandedMode()
        var mOneHandedMode by remember { mutableStateOf(oneHandedMode) }

        val dynamicColor = settingsViewModel.getIsDynamicColor()
        var mDynamicColor by remember { mutableStateOf(dynamicColor) }

        val volumeSlideSteps = settingsViewModel.getIsSliderIncrementFivePercent()
        var mVolumeSlideSteps by remember { mutableStateOf(volumeSlideSteps) }

        val primary = MaterialTheme.colorScheme.primary.toArgb()

        CustomGroupTitleText(R.string.general)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mAutoClearClipboard = !mAutoClearClipboard
                settingsViewModel.setIsAutoClearClipboard(mAutoClearClipboard)
            }
        ) {
            Text(text = stringResource(R.string.clear_clipboard_on_launch))
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mAutoClearClipboard,
                onCheckedChange = {
                    mAutoClearClipboard = it
                    settingsViewModel.setIsAutoClearClipboard(it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mVolumeSlideSteps = !mVolumeSlideSteps
                settingsViewModel.setIsSliderIncrementFivePercent(mVolumeSlideSteps)
            }
        ) {
            Text(text = stringResource(R.string.set_slider_increment_5))
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mVolumeSlideSteps,
                onCheckedChange = {
                    mVolumeSlideSteps = it
                    settingsViewModel.setIsSliderIncrementFivePercent(it)
                }
            )
        }


        if (OsVersion.android12()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    mDynamicColor = !mDynamicColor
                    onClickDynamicColorButton(mDynamicColor, primary, view)
                }
            ) {
                Text(text = stringResource(R.string.material_you_dynamic_color))
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = mDynamicColor,
                    onCheckedChange = {
                        mDynamicColor = it
                        onClickDynamicColorButton(it, primary, view)
                    }
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mOneHandedMode = !mOneHandedMode
                settingsViewModel.setIsOneHandedMode(mOneHandedMode)
            }
        ) {
            Text(text = stringResource(R.string.one_handed_mode))
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mOneHandedMode,
                onCheckedChange = {
                    mOneHandedMode = it
                    settingsViewModel.setIsOneHandedMode(it)
                }
            )
        }

        CustomGroupDivider()
        CustomGroupTitleText(R.string.customize)

        Button(
            onClick = { navController.navigate(EDIT_HOME_PAGE_SCREEN) }
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            CustomIconAndTextPadding()
            Text(text = stringResource(R.string.customize_my_home_page))
        }

        CustomGroupDivider()
        CustomGroupTitleText(R.string.debugging)

        val context = LocalContext.current

        CustomAlertDialogButton(
            buttonText = stringResource(R.string.erase_all_app_data),
            dialogMessageTitle = stringResource(R.string.warning),
            dialogMessage = {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.warning_erase_all_data_1))
                        append(" ")
                        CustomBoldText(R.string.warning_erase_all_data_2)
                        append("\n\n")
                        append(stringResource(R.string.warning_erase_all_data_3))
                    }
                )
            },
            positiveButtonText = stringResource(R.string.erase_all_data),
            negativeButtonText = null,
            onClickAction = {
                settingsViewModel.clear()
                (context as Activity).finish()
            }
        )
    }
}

private fun onClickDynamicColorButton(isDynamicColor: Boolean, color: Int, view: View) {
    val context = view.context
    SettingsViewModel.setIsDynamicColor(isDynamicColor)

    TSnackbar(view).snackbar(
        message = context.getString(R.string.requires_restart_do_it_now),
        buttonText = context.getString(R.string.restart),
        buttonColor = color,
        buttonClickListener = { TIntent.restartApp(context) }
    )
}