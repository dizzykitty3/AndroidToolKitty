package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Terminal
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
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.CARD_3
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_DEBUGGING
import me.dizzykitty3.androidtoolkitty.SCR_EDIT_HOME
import me.dizzykitty3.androidtoolkitty.SCR_LICENSES
import me.dizzykitty3.androidtoolkitty.SOURCE_CODE
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CardSpacePadding
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.utils.ToastUtil.showToast

@Composable
fun Settings(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    Screen {
        if (BuildConfig.DEBUG) {
            Debugging(settingsViewModel, navController)
            CardSpacePadding()
        }
        Appearance(settingsViewModel)
        General(settingsViewModel, navController)
        Bottom(navController)
    }
}

@Composable
private fun Appearance(settingsViewModel: SettingsViewModel) {
    val view = LocalView.current
    var dynamicColor by remember { mutableStateOf(settingsViewModel.settings.value.dynamicColor) }
    var fadeAnimation by remember { mutableStateOf(settingsViewModel.settings.value.fadeAnimation) }

    Card(R.string.appearance) {
        if (OSVersion.a12()) {
            CustomSwitchRow(R.string.material_you_dynamic_color, dynamicColor) {
                view.hapticFeedback()
                dynamicColor = it
                settingsViewModel.update(settingsViewModel.settings.value.copy(dynamicColor = it))
            }
        }

        CustomSwitchRow(R.string.switch_to_fade_animation, fadeAnimation) {
            view.hapticFeedback()
            fadeAnimation = it
            settingsViewModel.update(settingsViewModel.settings.value.copy(fadeAnimation = it))
        }
    }
}

@Composable
private fun General(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var autoClearClipboard by remember { mutableStateOf(settingsSharedPref.autoClearClipboard) }
    var showClipboardCard by remember { mutableStateOf(settingsSharedPref.getCardShowedState(CARD_3)) }
    val inversePrimary = MaterialTheme.colorScheme.inversePrimary.toArgb()
    val inverseOnSurface = MaterialTheme.colorScheme.inverseOnSurface.toArgb()

    Card(R.string.general) {
        CustomSwitchRow(R.string.clear_clipboard_on_launch, autoClearClipboard) {
            view.hapticFeedback()
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
                        view.hapticFeedback()
                        settingsSharedPref.saveCardShowedState(CARD_3, true)
                    }
                )
            }
            settingsSharedPref.autoClearClipboard = autoClearClipboard
        }

        GroupDivider()

        OutlinedButton({
            view.hapticFeedback()
            navController.navigate(SCR_EDIT_HOME)
        }
        ) {
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

    Column {
        TextButton({
            view.hapticFeedback()
            view.context.showToast(R.string.all_help_welcomed)
            view.context.openURL(SOURCE_CODE)
        }) {
            Icon(imageVector = Icons.Outlined.Code, contentDescription = null)
            SpacerPadding()
            Text(stringResource(R.string.view_source_code))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(R.string.view_source_code),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        TextButton({
            view.hapticFeedback()
            navController.navigate(SCR_LICENSES)
        }) {
            Icon(imageVector = Icons.Outlined.Bookmarks, contentDescription = null)
            SpacerPadding()
            Text(stringResource(R.string.licenses))
        }

        TextButton({
            view.hapticFeedback()
            navController.navigate(SCR_DEBUGGING)
        }) {
            Icon(imageVector = Icons.Outlined.Terminal, contentDescription = null)
            SpacerPadding()
            Text(stringResource(R.string.debugging))
        }
    }
}