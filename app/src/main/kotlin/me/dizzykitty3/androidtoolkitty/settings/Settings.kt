package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.CARD_3
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_EDIT_HOME
import me.dizzykitty3.androidtoolkitty.SCR_LICENSES
import me.dizzykitty3.androidtoolkitty.SOURCE_CODE_URL
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomSwitchRow
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
        Appearance(settingsViewModel)
        General(settingsViewModel, navController)
        Bottom(navController)
    }
}

@Composable
private fun Appearance(settingsViewModel: SettingsViewModel) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    var dynamicColor by remember { mutableStateOf(settingsViewModel.settings.value.dynamicColor) }
    var forceDarkMode by remember { mutableStateOf(settingsViewModel.settings.value.forceDarkMode) }
    var dismissLangTip by remember { mutableStateOf(settingsViewModel.settings.value.dismissLangTip) }
    var hideGreetings by remember { mutableStateOf(settingsViewModel.settings.value.hideGreetings) }

    Card(R.string.appearance) {
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

    Card(R.string.general) {
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
    }
}

@Composable
private fun Bottom(navController: NavHostController) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current

    Column {
        Surface(shape = RoundedCornerShape(8.dp)) {
            Row(Modifier.clickable {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                view.context.showToast(R.string.all_help_welcomed)
                view.context.openURL(SOURCE_CODE_URL)
            }) {
                Icon(
                    imageVector = Icons.Outlined.Code,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
                SpacerPadding()
                Text(
                    stringResource(R.string.view_source_code),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = stringResource(R.string.view_source_code),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
            }
        }
        SpacerPadding()
        SpacerPadding()
        Surface(shape = RoundedCornerShape(8.dp)) {
            Row(Modifier.clickable {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                navController.navigate(SCR_LICENSES)
            }) {
                Icon(
                    imageVector = Icons.Outlined.Bookmarks,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
                SpacerPadding()
                Text(
                    stringResource(R.string.licenses),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
            }
        }
        SpacerPadding()
        SpacerPadding()
        Surface(shape = RoundedCornerShape(8.dp)) {
            Row(Modifier.clickable {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                view.context.openAppDetailSettings()
            }) {
                Icon(
                    imageVector = Icons.Outlined.SettingsApplications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
                SpacerPadding()
                Text(
                    stringResource(R.string.open_app_detail_settings),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = stringResource(R.string.open_app_detail_settings),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
            }
        }
        SpacerPadding()
        SpacerPadding()
        Surface(shape = RoundedCornerShape(8.dp)) {
            Row(Modifier.clickable {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                view.context.restartApp()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
                SpacerPadding()
                Text(
                    stringResource(R.string.restart_app),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7F)
                )
            }
        }
    }
}