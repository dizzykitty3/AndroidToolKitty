package me.dizzykitty3.androidtoolkitty.ui.screens.settings

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.CARD_3
import me.dizzykitty3.androidtoolkitty.data.DEBUGGING_SCREEN
import me.dizzykitty3.androidtoolkitty.data.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.data.LICENSES_SCREEN
import me.dizzykitty3.androidtoolkitty.data.SOURCE_CODE_URL
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.restartApp
import me.dizzykitty3.androidtoolkitty.domain.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.domain.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.domain.utils.ToastUtil.showToast
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.ui.components.Screen
import me.dizzykitty3.androidtoolkitty.ui.components.SpacerPadding
import me.dizzykitty3.androidtoolkitty.ui.viewmodel.SettingsViewModel

@Composable
fun Settings(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    Screen {
        Appearance()
        General(navController)
        Bottom(navController)
    }
}

@Composable
private fun Appearance() {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var oneHandedMode by remember { mutableStateOf(settingsSharedPref.oneHandedMode) }
    var dynamicColor by remember { mutableStateOf(settingsSharedPref.dynamicColor) }

    Card(title = R.string.appearance) {
        if (OSVersion.a12()) {
            CustomSwitchRow(text = R.string.material_you_dynamic_color, checked = dynamicColor) {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                dynamicColor = it
                SettingsSharedPref.dynamicColor = it
                view.context.restartApp()
            }
        }

        CustomSwitchRow(text = R.string.one_handed_mode, checked = oneHandedMode) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            oneHandedMode = it
            settingsSharedPref.oneHandedMode = it
        }
    }
}

@Composable
private fun General(navController: NavHostController) {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var autoClearClipboard by remember { mutableStateOf(settingsSharedPref.autoClearClipboard) }
    var showClipboardCard by remember { mutableStateOf(settingsSharedPref.getCardShowedState(CARD_3)) }
    val inversePrimary = MaterialTheme.colorScheme.inversePrimary.toArgb()
    val inverseOnSurface = MaterialTheme.colorScheme.inverseOnSurface.toArgb()
    var webpageShowMore by remember { mutableStateOf(settingsSharedPref.keepWebpageCardShowMore) }

    Card(title = R.string.general) {
        CustomSwitchRow(
            text = R.string.clear_clipboard_on_launch,
            checked = autoClearClipboard
        ) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            autoClearClipboard = it
            // Automatically hide Clipboard Card when turning on Clear on Launch feature.
            if (autoClearClipboard && showClipboardCard) {
                showClipboardCard = false
                settingsSharedPref.saveCardShowedState(CARD_3, false)
                view.showSnackbar(
                    message = R.string.clipboard_card_hidden,
                    buttonText = R.string.undo,
                    textColor = inverseOnSurface,
                    buttonColor = inversePrimary,
                    buttonClickListener = {
                        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                        settingsSharedPref.saveCardShowedState(CARD_3, true)
                    }
                )
            }
            settingsSharedPref.autoClearClipboard = autoClearClipboard
        }

        CustomSwitchRow(
            text = R.string.keep_showing_full_webpage_card,
            checked = webpageShowMore
        ) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            webpageShowMore = it
            settingsSharedPref.keepWebpageCardShowMore = it
        }

        OutlinedButton(
            onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                navController.navigate(EDIT_HOME_SCREEN)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = stringResource(id = R.string.customize_my_home_page),
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

    Row(
        Modifier.horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton({
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            view.context.showToast(R.string.all_help_welcomed)
            view.context.openURL(SOURCE_CODE_URL)
        }) {
            Text(stringResource(R.string.source_code))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(R.string.source_code),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text("|")
        TextButton({
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            navController.navigate(LICENSES_SCREEN)
        }) { Text(stringResource(R.string.licenses)) }
        Text("|")
        TextButton({
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            navController.navigate(DEBUGGING_SCREEN)
        }) { Text(stringResource(R.string.debugging)) }
    }
}