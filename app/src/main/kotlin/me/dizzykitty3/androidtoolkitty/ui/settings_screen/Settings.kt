package me.dizzykitty3.androidtoolkitty.ui.settings_screen

import android.content.Context
import android.view.HapticFeedbackConstants
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.CARD_3
import me.dizzykitty3.androidtoolkitty.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui_components.CustomCard
import me.dizzykitty3.androidtoolkitty.ui_components.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.ui_components.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil

@Composable
fun Appearance() {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var oneHandedMode by remember { mutableStateOf(settingsSharedPref.oneHandedMode) }
    var dynamicColor by remember { mutableStateOf(settingsSharedPref.dynamicColor) }
    var showEditVolumeOption by remember { mutableStateOf(settingsSharedPref.showEditVolumeOption) }
    val webpageShowMore = settingsSharedPref.enabledWebpageCardShowMore
    val showWebpageShowMoreOption = remember { webpageShowMore }
    var mWebpageShowMore by remember { mutableStateOf(webpageShowMore) }

    CustomCard(titleRes = R.string.appearance) {
        if (OSVersion.android12()) {
            CustomSwitchRow(textRes = R.string.material_you_dynamic_color, checked = dynamicColor) {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                dynamicColor = it
                onClickDynamicColorButton(it, view.context)
            }
        }

        CustomSwitchRow(textRes = R.string.one_handed_mode, checked = oneHandedMode) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            oneHandedMode = it
            settingsSharedPref.oneHandedMode = it
        }

        if (settingsSharedPref.addedCustomVolume) CustomSwitchRow(
            textRes = R.string.show_edit_volume_option,
            checked = showEditVolumeOption
        ) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            showEditVolumeOption = it
            settingsSharedPref.showEditVolumeOption = it
        }

        if (showWebpageShowMoreOption) CustomSwitchRow(
            textRes = R.string.show_full_webpage_card,
            checked = mWebpageShowMore
        ) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            mWebpageShowMore = it
            settingsSharedPref.webpageCardShowMore = it
        }
    }
}

private fun onClickDynamicColorButton(isDynamicColor: Boolean, context: Context) {
    SettingsSharedPref.dynamicColor = isDynamicColor
    IntentUtil.restartApp(context)
}

@Composable
fun General() {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var autoClearClipboard by remember { mutableStateOf(settingsSharedPref.autoClearClipboard) }
    var showClipboardCard by remember { mutableStateOf(settingsSharedPref.getCardShowedState(CARD_3)) }
    val inversePrimary = MaterialTheme.colorScheme.inversePrimary.toArgb()
    val inverseOnSurface = MaterialTheme.colorScheme.inverseOnSurface.toArgb()

    CustomCard(titleRes = R.string.general) {
        CustomSwitchRow(
            textRes = R.string.clear_clipboard_on_launch,
            checked = autoClearClipboard
        ) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            autoClearClipboard = it
            // Automatically hide Clipboard Card when turning on Clear on Launch feature.
            if (autoClearClipboard && showClipboardCard) {
                showClipboardCard = false
                settingsSharedPref.saveCardShowedState(CARD_3, false)
                SnackbarUtil.show(
                    view,
                    messageRes = R.string.clipboard_card_hidden,
                    buttonTextRes = R.string.undo,
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
    }
}

@Composable
fun Customize(navController: NavHostController) {
    val view = LocalView.current

    CustomCard(titleRes = R.string.customize) {
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
            Text(text = stringResource(R.string.customize_my_home_page))
        }
    }
}