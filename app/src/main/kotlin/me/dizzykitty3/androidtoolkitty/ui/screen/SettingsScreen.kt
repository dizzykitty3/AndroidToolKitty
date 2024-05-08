package me.dizzykitty3.androidtoolkitty.ui.screen

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_3
import me.dizzykitty3.androidtoolkitty.foundation.const.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.foundation.const.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.foundation.util.IntentUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.OsVersion
import me.dizzykitty3.androidtoolkitty.foundation.util.SnackbarUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.ToastUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.UrlUtil
import java.util.Locale

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
            @Suppress("KotlinConstantConditions")
            AnimatedVisibility(
                visible = (debuggingOptions || (!debuggingOptions && tapCount >= 5)),
                enter = fadeIn(animationSpec = tween(durationMillis = 2000))
            ) {
                Column {
                    CustomGroupDivider()
                    DebuggingOptions(navController)
                }
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
                        when (tapCount) {
                            3 -> SnackbarUtil.snackbar(view, R.string.two_more_times)
                            4 -> SnackbarUtil.snackbar(view, R.string.one_more_time)
                            5 -> {
                                SnackbarUtil.snackbar(view, R.string.now_a_developer)
                                SettingsSharedPref.debuggingOptions = true
                            }
                        }
                    } else {
                        SnackbarUtil.snackbar(view, R.string.already_developer)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = stringResource(id = R.string.version)
                )
                CustomIconAndTextPadding()

                @Suppress("KotlinConstantConditions")
                val debugVariant = BuildConfig.BUILD_TYPE == "debug"

                @Suppress("KotlinConstantConditions")
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
    val context = LocalContext.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var autoClearClipboard by remember { mutableStateOf(settingsSharedPref.autoClearClipboard) }
    var showClipboardCard by remember { mutableStateOf(settingsSharedPref.getCardShowedState(CARD_3)) }
    var oneHandedMode by remember { mutableStateOf(settingsSharedPref.oneHandedMode) }
    var dynamicColor by remember { mutableStateOf(settingsSharedPref.dynamicColor) }
    var volumeSlideSteps by remember { mutableStateOf(settingsSharedPref.sliderIncrement5Percent) }
    var soraShion by remember { mutableStateOf(settingsSharedPref.soraShion) }
    var collapseKeyboard by remember { mutableStateOf(settingsSharedPref.collapseKeyboard) }

    val primary = MaterialTheme.colorScheme.primary.toArgb()

    CustomGroupTitleText(R.string.general)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            autoClearClipboard = !autoClearClipboard
            // Automatically hide Clipboard Card when turning on Clear on Launch feature.
            if (autoClearClipboard && showClipboardCard) {
                showClipboardCard = false
                settingsSharedPref.saveCardShowedState(CARD_3, false)
                SnackbarUtil.snackbar(
                    view,
                    message = R.string.clipboard_card_hidden,
                    buttonText = R.string.undo,
                    buttonColor = primary,
                    buttonClickListener = {
                        settingsSharedPref.saveCardShowedState(CARD_3, true)
                    }
                )
            }
            settingsSharedPref.autoClearClipboard = autoClearClipboard
        }
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.clear_clipboard_on_launch))
        }
        Column {
            val primaryColor = MaterialTheme.colorScheme.primary.toArgb()

            Switch(
                checked = autoClearClipboard,
                onCheckedChange = {
                    autoClearClipboard = it
                    if (autoClearClipboard) {
                        settingsSharedPref.saveCardShowedState(CARD_3, false)
                        SnackbarUtil.snackbar(
                            view,
                            message = R.string.clipboard_card_hidden,
                            buttonText = R.string.undo,
                            buttonColor = primaryColor,
                            buttonClickListener = {
                                settingsSharedPref.saveCardShowedState(CARD_3, true)
                            }
                        )
                    }
                    settingsSharedPref.autoClearClipboard = it
                }
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            volumeSlideSteps = !volumeSlideSteps
            settingsSharedPref.sliderIncrement5Percent = volumeSlideSteps
        }
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.set_slider_increment_5))
        }
        Column {
            Switch(
                checked = volumeSlideSteps,
                onCheckedChange = {
                    volumeSlideSteps = it
                    settingsSharedPref.sliderIncrement5Percent = it
                }
            )
        }
    }

    if (OsVersion.android12()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                dynamicColor = !dynamicColor
                soraShion = false
                onClickDynamicColorButton(view, dynamicColor, primary, context)
            }
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.material_you_dynamic_color))
            }
            Column {
                Switch(
                    checked = dynamicColor,
                    onCheckedChange = {
                        dynamicColor = it
                        soraShion = false
                        onClickDynamicColorButton(view, it, primary, context)
                    }
                )
            }
        }
    }

    if (!dynamicColor || !OsVersion.android12()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                soraShion = !soraShion
                onClickSoraShionButton(view, soraShion, primary, context)
            }
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(id = R.string.theme_2))
            }
            Column {
                Switch(
                    checked = soraShion,
                    onCheckedChange = {
                        soraShion = it
                        onClickSoraShionButton(view, it, primary, context)
                    }
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            oneHandedMode = !oneHandedMode
            settingsSharedPref.oneHandedMode = oneHandedMode
        }
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.one_handed_mode))
        }
        Column {
            Switch(
                checked = oneHandedMode,
                onCheckedChange = {
                    oneHandedMode = it
                    settingsSharedPref.oneHandedMode = it
                }
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            collapseKeyboard = !collapseKeyboard
            settingsSharedPref.collapseKeyboard = collapseKeyboard
        }
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(id = R.string.collapse_keyboard_when_back_to_app))
        }
        Column {
            Switch(
                checked = collapseKeyboard,
                onCheckedChange = {
                    collapseKeyboard = it
                    settingsSharedPref.collapseKeyboard = it
                }
            )
        }
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
    val view = LocalView.current
    val context = LocalContext.current
    val settingsSharedPref = remember { SettingsSharedPref }

    CustomGroupTitleText(R.string.debugging)

    Text(text = "Android ${Build.VERSION.RELEASE}, API ${Build.VERSION.SDK_INT}")
    Text(text = "Language =  ${Locale.getDefault()}")

    CustomSpacerPadding()

    Button(
        onClick = { navController.navigate(PERMISSION_REQUEST_SCREEN) }
    ) {
        Text(text = stringResource(id = R.string.go_to_permission_request_screen))
    }

    Button(
        onClick = { SnackbarUtil.snackbar(view, R.string.under_development) }
    ) {
        Text(text = stringResource(id = R.string.check_sp_values))
    }

    Button(
        onClick = { IntentUtil.restartApp(context) }
    ) {
        Text(text = stringResource(id = R.string.restart_app))
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

private fun onClickDynamicColorButton(
    view: View,
    isDynamicColor: Boolean,
    color: Int,
    context: Context
) {
    SettingsSharedPref.dynamicColor = isDynamicColor
    SettingsSharedPref.soraShion = false

    SnackbarUtil.snackbar(
        view,
        message = R.string.requires_restart_do_it_now,
        buttonText = R.string.restart,
        buttonColor = color,
        buttonClickListener = { IntentUtil.restartApp(context) }
    )
}

private fun onClickSoraShionButton(view: View, isSoraShion: Boolean, color: Int, context: Context) {
    SettingsSharedPref.soraShion = isSoraShion

    SnackbarUtil.snackbar(
        view,
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