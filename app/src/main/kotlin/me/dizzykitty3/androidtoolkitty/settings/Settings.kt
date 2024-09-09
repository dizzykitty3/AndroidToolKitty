package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.CARD_3
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_EDIT_HOME
import me.dizzykitty3.androidtoolkitty.SCR_LICENSES
import me.dizzykitty3.androidtoolkitty.SOURCE_CODE_URL
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.CardSpacePadding
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.uicomponents.Description
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupTitle
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppLanguageSetting
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.restartApp
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.utils.ToastUtil.showToast

@Composable
fun Settings(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    Screen {
        SettingsTitle()
        Appearance(settingsViewModel)
        General(settingsViewModel, navController)
        Bottom(navController)
    }
}

@Composable
private fun SettingsTitle() {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineSmall
        )
    }
    SpacerPadding()
}

@Composable
private fun Appearance(settingsViewModel: SettingsViewModel) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    var dynamicColor by remember { mutableStateOf(settingsViewModel.settings.value.dynamicColor) }
    var forceDarkMode by remember { mutableStateOf(settingsViewModel.settings.value.forceDarkMode) }
    var dismissLangTip by remember { mutableStateOf(settingsViewModel.settings.value.dismissLangTip) }
    var hideGreetings by remember { mutableStateOf(settingsViewModel.settings.value.hideGreetings) }

    Column {
        GroupTitle(R.string.appearance)

        if (OSVersion.android12()) {
            CustomSwitchRow(
                R.string.dynamic_color,
                R.string.material_you_dynamic_color_theme,
                dynamicColor
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                dynamicColor = it
                settingsViewModel.update(settingsViewModel.settings.value.copy(dynamicColor = it))
            }
            SpacerPadding()
        }

        CustomSwitchRow(
            R.string.force_dark_mode,
            R.string.force_dark_mode_description,
            forceDarkMode
        ) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            forceDarkMode = it
            settingsViewModel.update(settingsViewModel.settings.value.copy(forceDarkMode = it))
        }
        SpacerPadding()

        CustomSwitchRow(
            R.string.dismiss_lang_tip,
            R.string.dismiss_lang_tip_description,
            dismissLangTip
        ) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            dismissLangTip = it
            settingsViewModel.update(settingsViewModel.settings.value.copy(dismissLangTip = it))
        }
        SpacerPadding()

        CustomSwitchRow(
            R.string.hide_greetings,
            R.string.hide_greetings_description,
            hideGreetings
        ) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            hideGreetings = it
            settingsViewModel.update(settingsViewModel.settings.value.copy(hideGreetings = it))
        }

        if (OSVersion.android13()) {
            SpacerPadding()
            OutlinedButton({
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                view.context.openAppLanguageSetting()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Language,
                    contentDescription = stringResource(R.string.change_app_language),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                SpacerPadding()
                Text(stringResource(R.string.change_app_language))
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = stringResource(R.string.change_app_language),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        GroupDivider()
    }
}

@Composable
private fun General(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var autoClearClipboard by remember { mutableStateOf(settingsViewModel.settings.value.autoClearClipboard) }
    var showClipboardCard by remember { mutableStateOf(settingsSharedPref.getShownState(CARD_3)) }
    val inversePrimary = MaterialTheme.colorScheme.inversePrimary.toArgb()
    val inverseOnSurface = MaterialTheme.colorScheme.inverseOnSurface.toArgb()

    Column {
        GroupTitle(R.string.general)

        CustomSwitchRow(
            R.string.clear_clipboard_automatically,
            R.string.clear_clipboard_on_launch,
            autoClearClipboard
        ) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            autoClearClipboard = it
            // Automatically hide Clipboard Card when turning on Clear on Launch feature.
            if (autoClearClipboard && showClipboardCard) {
                showClipboardCard = false
                settingsSharedPref.saveShownState(CARD_3, false)
                view.showSnackbar(
                    message = R.string.clipboard_card_hidden,
                    buttonText = R.string.undo,
                    textColor = inverseOnSurface,
                    buttonColor = inversePrimary,
                    buttonClickListener = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        settingsSharedPref.saveShownState(CARD_3, true)
                    }
                )
            }
            settingsViewModel.update(settingsViewModel.settings.value.copy(autoClearClipboard = it))
        }
        SpacerPadding()

        OutlinedButton({
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_EDIT_HOME)
        }) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = stringResource(R.string.customize_my_home_page),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SpacerPadding()
            Text(stringResource(R.string.customize_my_home_page))
        }

        GroupDivider()
    }
}

@Composable
private fun Bottom(navController: NavHostController) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current

    Column {
        GroupTitle("Others")

        // source code
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    view.context.showToast(R.string.all_help_welcomed)
                    view.context.openURL(SOURCE_CODE_URL)
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Row(Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Code,
                    contentDescription = null
                )
                SpacerPadding()
                Column {
                    Text(stringResource(R.string.view_source_code))
                    Description(stringResource(R.string.view_source_code_description))
                }
            }
            CardSpacePadding()
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                contentDescription = stringResource(R.string.view_source_code)
            )
        }
        SpacerPadding()
        SpacerPadding()

        // licenses
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    navController.navigate(SCR_LICENSES)
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Bookmarks,
                contentDescription = null
            )
            SpacerPadding()
            Column {
                Text(stringResource(R.string.licenses))
                Description("Auto-generated by AboutLibraries")
            }
        }
        SpacerPadding()
        SpacerPadding()

        // android app settings
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    view.context.openAppDetailSettings()
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Row(Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.SettingsApplications,
                    contentDescription = null
                )
                SpacerPadding()
                Column {
                    Text(stringResource(R.string.open_app_detail_settings))
                    Description("Clear storage, manage permission, and so on")
                }
            }
            SpacerPadding()
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                contentDescription = stringResource(R.string.open_app_detail_settings)
            )
        }
        SpacerPadding()
        SpacerPadding()

        // relaunch
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    view.context.restartApp()
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = null
            )
            SpacerPadding()
            Column {
                Text(stringResource(R.string.restart_app))
                Description("For testing")
            }
        }
    }
}