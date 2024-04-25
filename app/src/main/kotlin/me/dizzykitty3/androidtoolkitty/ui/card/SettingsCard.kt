package me.dizzykitty3.androidtoolkitty.ui.card

import android.app.Activity
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomAlertDialogButton
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomBoldText
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupTitleText
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.foundation.const.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.foundation.util.IntentUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.OsVersion
import me.dizzykitty3.androidtoolkitty.foundation.util.SnackbarUtil

@Composable
fun SettingsCard(navController: NavHostController) {
    CustomCard(
        title = R.string.settings
    ) {
        GeneralSettingOptions()
        CustomGroupDivider()
        CustomSettingOptions(navController = navController)
        CustomGroupDivider()
        DebuggingSettingOptions()
    }
}

@Composable
private fun GeneralSettingOptions() {
    val view = LocalView.current

    val settingsSharedPref = remember { SettingsSharedPref }

    val autoClearClipboard = settingsSharedPref.autoClearClipboard
    var mAutoClearClipboard by remember { mutableStateOf(autoClearClipboard) }

    val oneHandedMode = settingsSharedPref.oneHandedMode
    var mOneHandedMode by remember { mutableStateOf(oneHandedMode) }

    val dynamicColor = settingsSharedPref.dynamicColor
    var mDynamicColor by remember { mutableStateOf(dynamicColor) }

    val volumeSlideSteps = settingsSharedPref.sliderIncrement5Percent
    var mVolumeSlideSteps by remember { mutableStateOf(volumeSlideSteps) }

    val primary = MaterialTheme.colorScheme.primary.toArgb()

    CustomGroupTitleText(R.string.general)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            mAutoClearClipboard = !mAutoClearClipboard
            settingsSharedPref.autoClearClipboard = mAutoClearClipboard
        }
    ) {
        Text(text = stringResource(R.string.clear_clipboard_on_launch))
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = mAutoClearClipboard,
            onCheckedChange = {
                mAutoClearClipboard = it
                settingsSharedPref.autoClearClipboard = it
            }
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            mVolumeSlideSteps = !mVolumeSlideSteps
            settingsSharedPref.sliderIncrement5Percent = mVolumeSlideSteps
        }
    ) {
        Text(text = stringResource(R.string.set_slider_increment_5))
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = mVolumeSlideSteps,
            onCheckedChange = {
                mVolumeSlideSteps = it
                settingsSharedPref.sliderIncrement5Percent = it
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
            settingsSharedPref.oneHandedMode = mOneHandedMode
        }
    ) {
        Text(text = stringResource(R.string.one_handed_mode))
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = mOneHandedMode,
            onCheckedChange = {
                mOneHandedMode = it
                settingsSharedPref.oneHandedMode = it
            }
        )
    }
}

@Composable
private fun CustomSettingOptions(navController: NavHostController) {
    CustomGroupTitleText(R.string.customize)

    Button(
        onClick = { navController.navigate(EDIT_HOME_SCREEN) },
        elevation = ButtonDefaults.buttonElevation(1.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Edit,
            contentDescription = stringResource(id = R.string.customize_my_home_page),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        CustomSpacerPadding()
        Text(text = stringResource(R.string.customize_my_home_page))
    }
}

@Composable
private fun DebuggingSettingOptions() {
    val context = LocalContext.current
    val settingsSharedPref = remember { SettingsSharedPref }

    CustomGroupTitleText(R.string.debugging)

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
            settingsSharedPref.clear()
            (context as Activity).finish()
        }
    )
}

private fun onClickDynamicColorButton(isDynamicColor: Boolean, color: Int, view: View) {
    val context = view.context
    SettingsSharedPref.dynamicColor = isDynamicColor

    SnackbarUtil(view).snackbar(
        message = R.string.requires_restart_do_it_now,
        buttonText = R.string.restart,
        buttonColor = color,
        buttonClickListener = { IntentUtil.restartApp(context) }
    )
}