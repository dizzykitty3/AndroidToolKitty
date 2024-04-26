package me.dizzykitty3.androidtoolkitty.ui.screen

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomAlertDialogButton
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomBoldText
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupTitleText
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomIconAndTextPadding
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomScreen
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.foundation.const.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.foundation.const.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.foundation.util.IntentUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.OsVersion
import me.dizzykitty3.androidtoolkitty.foundation.util.SnackbarUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.ToastUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.UrlUtil
import java.util.Locale

@Suppress("KotlinConstantConditions")
@Composable
fun SettingsScreen(navController: NavHostController) {
    CustomScreen {
        val view = LocalView.current
        val debuggingOptions = SettingsSharedPref.debuggingOptions
        var tapCount by remember { mutableIntStateOf(0) }

        CustomCard(
            title = R.string.settings
        ) {
            GeneralOptions()
            CustomGroupDivider()
            CustomizeOptions(navController = navController)
            if (debuggingOptions || (!debuggingOptions && tapCount >= 5)) {
                CustomGroupDivider()
                DebuggingOptions(navController)
            }
        }

        CustomCard(
            title = R.string.about
        ) {
            CustomGroupTitleText(id = R.string.version)
            Row(
                modifier = Modifier.clickable {
                    if (!debuggingOptions) {
                        tapCount++
                        if (tapCount == 5) {
                            SnackbarUtil(view).snackbar(R.string.now_a_developer)
                            SettingsSharedPref.debuggingOptions = true
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = stringResource(id = R.string.version)
                )
                CustomIconAndTextPadding()

                val debugVariant = BuildConfig.BUILD_TYPE == "debug"
                val versionInfo =
                    if (debugVariant)
                        "${stringResource(R.string.version)} ${stringResource(R.string.version_number)} dev"
                    else
                        "${stringResource(R.string.version)} ${stringResource(R.string.version_number)}"
                Text(text = versionInfo)
            }
            CustomGroupDivider()
            Contributor()
            CustomGroupDivider()
            SourceAndLicenses()
        }
    }
}

@Composable
private fun GeneralOptions() {
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

    val soraShion = settingsSharedPref.soraShion
    var mSoraShion by remember { mutableStateOf(soraShion) }

    val collapseKeyboard = settingsSharedPref.collapseKeyboard
    var mCollapseKeyboard by remember { mutableStateOf(collapseKeyboard) }

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
                mSoraShion = false
                onClickDynamicColorButton(mDynamicColor, primary, view)
            }
        ) {
            Text(text = stringResource(R.string.material_you_dynamic_color))
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mDynamicColor,
                onCheckedChange = {
                    mDynamicColor = it
                    mSoraShion = false
                    onClickDynamicColorButton(it, primary, view)
                }
            )
        }
    }

    if (!mDynamicColor || !OsVersion.android12()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mSoraShion = !mSoraShion
                onClickSoraShionButton(mSoraShion, primary, view)
            }
        ) {
            Text(text = "theme 2 (sora - shion)")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mSoraShion,
                onCheckedChange = {
                    mSoraShion = it
                    onClickSoraShionButton(it, primary, view)
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

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            mCollapseKeyboard = !mCollapseKeyboard
            settingsSharedPref.collapseKeyboard = mCollapseKeyboard
        }
    ) {
        Text(text = "collapse keyboard when back to toolkitty app")
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = mCollapseKeyboard,
            onCheckedChange = {
                mCollapseKeyboard = it
                settingsSharedPref.collapseKeyboard = it
            }
        )
    }
}

@Composable
private fun CustomizeOptions(navController: NavHostController) {
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
private fun DebuggingOptions(navController: NavHostController) {
    val context = LocalContext.current
    val settingsSharedPref = remember { SettingsSharedPref }

    CustomGroupTitleText(R.string.debugging)

    Text(text = "sdk version = ${Build.VERSION.SDK_INT}")
    Text(text = "lang code = ${Locale.getDefault()}")

    CustomSpacerPadding()

    TextButton(
        onClick = { navController.navigate(PERMISSION_REQUEST_SCREEN) }
    ) {
        Text(text = "go to permission request screen")
    }

    TextButton(
        onClick = { ToastUtil.toast("under development") }
    ) {
        Text(text = "check sharedpreferences values")
    }

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
    SettingsSharedPref.soraShion = false

    SnackbarUtil(view).snackbar(
        message = R.string.requires_restart_do_it_now,
        buttonText = R.string.restart,
        buttonColor = color,
        buttonClickListener = { IntentUtil.restartApp(context) }
    )
}

private fun onClickSoraShionButton(isSoraShion: Boolean, color: Int, view: View) {
    val context = view.context
    SettingsSharedPref.soraShion = isSoraShion

    SnackbarUtil(view).snackbar(
        message = R.string.requires_restart_do_it_now,
        buttonText = R.string.restart,
        buttonColor = color,
        buttonClickListener = { IntentUtil.restartApp(context) }
    )
}

@Composable
private fun Contributor() {
    CustomGroupTitleText(id = R.string.contributors)
    DeveloperProfileLink("dizzykitty3")
    CustomSpacerPadding()
    DeveloperProfileLink("HongjieCN")
}

@Composable
private fun DeveloperProfileLink(
    name: String
) {
    Row {
        val context = LocalContext.current

        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = stringResource(id = R.string.developer_profile_link)
        )
        CustomIconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                IntentUtil.openUrl(
                    "${UrlUtil.profilePrefix(UrlUtil.Platform.GITHUB)}$name",
                    context
                )
            }
        ) {
            Text(
                text = name,
                color = MaterialTheme.colorScheme.primary
            )

            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SourceAndLicenses() {
    CustomGroupTitleText(id = R.string.source_and_licenses)
    GitHubRepoLink()
}

@Composable
private fun GitHubRepoLink() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        val sourceCodeUrl = "https://github.com/dizzykitty3/android_toolkitty"

        Icon(
            imageVector = Icons.Outlined.Code,
            contentDescription = stringResource(id = R.string.developer_profile_link)
        )
        CustomIconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                ToastUtil.toast(R.string.all_help_welcomed)
                IntentUtil.openUrl(sourceCodeUrl, context)
            }
        ) {
            Text(
                text = stringResource(R.string.source_code_on_github),
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(id = R.string.developer_profile_link),
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}