package me.dizzykitty3.androidtoolkitty.ui.settings_screen

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui_components.Bold
import me.dizzykitty3.androidtoolkitty.ui_components.CustomCard
import me.dizzykitty3.androidtoolkitty.ui_components.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.ui_components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui_components.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui_components.IconAndTextPadding
import me.dizzykitty3.androidtoolkitty.ui_components.SpacerPadding
import me.dizzykitty3.androidtoolkitty.ui_components.WarningAlertDialogButton
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil
import me.dizzykitty3.androidtoolkitty.utils.ToastUtil
import me.dizzykitty3.androidtoolkitty.utils.URLUtil
import java.util.Locale

@Composable
fun About(navController: NavHostController) {
    val view = LocalView.current
    val debuggingOptions = SettingsSharedPref.debuggingOptions
    var tapCount by remember { mutableIntStateOf(0) }

    CustomCard(titleRes = R.string.about) {
        GroupTitle(id = R.string.version)
        Row(
            modifier = Modifier.clickable {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
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
            IconAndTextPadding()
            Text(text = "${stringResource(R.string.version)} ${BuildConfig.VERSION_NAME}")
        }
        GroupDivider()
        ContributorAndThanksTo()
        GroupDivider()
        SourceAndLicenses()
    }

    @Suppress("KotlinConstantConditions")
    AnimatedVisibility(
        visible = (debuggingOptions || (!debuggingOptions && tapCount >= 5)),
        enter = fadeIn(animationSpec = tween(durationMillis = 2000))
    ) {
        Debugging(navController)
    }
}

@Composable
private fun ContributorAndThanksTo() {
    GroupTitle(id = R.string.contributors)
    DeveloperProfileLink("dizzykitty3")
    SpacerPadding()
    DeveloperProfileLink("HongjieCN")
    SpacerPadding()
    GroupTitle(id = R.string.inspired_by)
    ThanksTo("tengusw/share_to_clipboard")
    SpacerPadding()
    ThanksTo("hashemi-hossein/memory-guardian")
}

@Composable
private fun DeveloperProfileLink(name: String) {
    Row {
        val view = LocalView.current

        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = stringResource(id = R.string.developer_profile_link)
        )
        IconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                IntentUtil.openURL(
                    "${URLUtil.prefixOf(URLUtil.Platform.GITHUB)}$name",
                    view.context
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
private fun ThanksTo(link: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val view = LocalView.current
        val sourceCodeURL = "https://github.com/$link"

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .clickable {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    IntentUtil.openURL(sourceCodeURL, view.context)
                }
        ) {
            Text(
                text = link,
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
    GroupTitle(id = R.string.source_and_licenses)
    GitHubRepoLink()
}

@Composable
private fun GitHubRepoLink() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val view = LocalView.current
        val sourceCodeURL = "https://github.com/dizzykitty3/AndroidToolKitty"

        Icon(
            imageVector = Icons.Outlined.Code,
            contentDescription = stringResource(id = R.string.developer_profile_link)
        )
        IconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                ToastUtil.toast(R.string.all_help_welcomed)
                IntentUtil.openURL(sourceCodeURL, view.context)
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

@Composable
private fun Debugging(navController: NavHostController) {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var uiTesting by remember { mutableStateOf(settingsSharedPref.uiTesting) }

    CustomCard(titleRes = R.string.debugging) {
        Text(text = "OS version = Android ${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})")
        Text(text = "Locale =  ${Locale.getDefault()}")

        CustomSwitchRow(textRes = R.string.ui_testing, checked = uiTesting) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            uiTesting = it
            settingsSharedPref.uiTesting = it
        }

        OutlinedButton(onClick = {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            navController.navigate(PERMISSION_REQUEST_SCREEN)
        }) {
            Text(text = stringResource(id = R.string.go_to_permission_request_screen))
        }

        OutlinedButton(onClick = {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            IntentUtil.restartApp(view.context)
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
                IntentUtil.finishApp(view.context)
            }
        )
    }
}