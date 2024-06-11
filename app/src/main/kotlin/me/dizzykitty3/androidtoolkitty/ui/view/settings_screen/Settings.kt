package me.dizzykitty3.androidtoolkitty.ui.view.settings_screen

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.View
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
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.ui.component.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.component.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui.component.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil

@Composable
fun Settings(navController: NavHostController) {
    Appearance()
    General()

    CustomCard(titleRes = R.string.settings) {
        CustomizeOptions(navController = navController)
        GroupDivider()
        UserSyncSection()
        GroupDivider()
        RuleUpdateSection()
    }
}

@Composable
private fun Appearance() {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var oneHandedMode by remember { mutableStateOf(settingsSharedPref.oneHandedMode) }
    var dynamicColor by remember { mutableStateOf(settingsSharedPref.dynamicColor) }
    var showDivider by remember { mutableStateOf(settingsSharedPref.showDivider) }
    var showEditVolumeOption by remember { mutableStateOf(settingsSharedPref.showEditVolumeOption) }
    val webpageShowMore = settingsSharedPref.enabledWebpageCardShowMore()
    val showWebpageShowMoreOption = remember { webpageShowMore }
    var mWebpageShowMore by remember { mutableStateOf(webpageShowMore) }
    val inversePrimary = MaterialTheme.colorScheme.inversePrimary.toArgb()
    val inverseOnSurface = MaterialTheme.colorScheme.inverseOnSurface.toArgb()

    CustomCard(titleRes = R.string.appearance) {
        if (OSVersion.android12()) {
            CustomSwitchRow(textRes = R.string.material_you_dynamic_color, checked = dynamicColor) {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                dynamicColor = it
                onClickDynamicColorButton(view, it, inverseOnSurface, inversePrimary, view.context)
            }
        }

        CustomSwitchRow(textRes = R.string.one_handed_mode, checked = oneHandedMode) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            oneHandedMode = it
            settingsSharedPref.oneHandedMode = it
        }

        CustomSwitchRow(textRes = R.string.show_divider, checked = showDivider) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            showDivider = it
            settingsSharedPref.showDivider = it
        }

        if (settingsSharedPref.addedCustomVolume()) CustomSwitchRow(
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

private fun onClickDynamicColorButton(
    view: View,
    isDynamicColor: Boolean,
    inverseOnSurface: Int,
    inversePrimary: Int,
    context: Context
) {
    val showSnackbarToConfirm = SettingsSharedPref.showSnackbar

    SettingsSharedPref.dynamicColor = isDynamicColor
    if (showSnackbarToConfirm) {
        SnackbarUtil.snackbar(
            view,
            messageRes = R.string.requires_restart_do_it_now,
            buttonTextRes = R.string.restart,
            textColor = inverseOnSurface,
            buttonColor = inversePrimary,
            buttonClickListener = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                IntentUtil.restartApp(context)
            }
        )
    } else {
        IntentUtil.restartApp(context)
    }
}

@Composable
private fun General() {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var autoClearClipboard by remember { mutableStateOf(settingsSharedPref.autoClearClipboard) }
    var showClipboardCard by remember { mutableStateOf(settingsSharedPref.getCardShowedState(CARD_3)) }
    var volumeSlideSteps by remember { mutableStateOf(settingsSharedPref.sliderIncrement5Percent) }
    var showSnackbarToConfirm by remember { mutableStateOf(settingsSharedPref.showSnackbar) }
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
                SnackbarUtil.snackbar(
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

        CustomSwitchRow(textRes = R.string.set_slider_increment_5, checked = volumeSlideSteps) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            volumeSlideSteps = it
            settingsSharedPref.sliderIncrement5Percent = it
        }

        CustomSwitchRow(
            textRes = R.string.show_snackbar_to_confirm,
            checked = showSnackbarToConfirm
        ) {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            showSnackbarToConfirm = it
            settingsSharedPref.showSnackbar = it
        }
    }
}

@Composable
private fun CustomizeOptions(navController: NavHostController) {
    val view = LocalView.current

    GroupTitle(R.string.customize)

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