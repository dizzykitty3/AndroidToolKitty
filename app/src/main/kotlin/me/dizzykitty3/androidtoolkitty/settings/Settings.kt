package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.dimensionResource
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
import me.dizzykitty3.androidtoolkitty.uicomponents.IconAndTextPadding
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppLanguageSetting
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar

@Composable
fun Settings(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    Screen(R.string.settings, navController) {
        if (OSVersion.android12()) Card(R.string.appearance) { Appearance(settingsViewModel) }
        Card(R.string.general) { General(settingsViewModel, navController) }
        Card(R.string.others) { Bottom(navController) }
    }
}

@Composable
private fun Appearance(settingsViewModel: SettingsViewModel) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    var dynamicColor by remember { mutableStateOf(settingsViewModel.settings.value.dynamicColor) }

    if (OSVersion.android12()) {
        CustomSwitchRow(
            icon = Icons.Outlined.ColorLens,
            title = R.string.dynamic_color,
            checked = dynamicColor
        ) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            dynamicColor = it
            settingsViewModel.update(settingsViewModel.settings.value.copy(dynamicColor = it))
        }
    }

    // change app lang
    if (OSVersion.android13()) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            Column(
                Modifier
                    .height(dimensionResource(R.dimen.height_setting_row))
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        view.context.openAppLanguageSetting()
                    }, verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Language,
                            contentDescription = stringResource(R.string.language)
                        )
                        IconAndTextPadding()
                        Text(stringResource(R.string.language))
                    }
                    SpacerPadding()
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
                    )
                    SpacerPadding()
                }
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
    var switchToBingSearch by remember { mutableStateOf(settingsViewModel.settings.value.switchToBingSearch) }
    var showClipboardCard by remember { mutableStateOf(settingsSharedPref.getShownState(CARD_3)) }
    val inversePrimary = MaterialTheme.colorScheme.inversePrimary.toArgb()
    val inverseOnSurface = MaterialTheme.colorScheme.inverseOnSurface.toArgb()

    CustomSwitchRow(
        icon = Icons.Outlined.ClearAll,
        title = R.string.clear_clipboard_on_launch,
        checked = autoClearClipboard
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

    // Switch to Bing Search
    CustomSwitchRow(
        icon = Icons.Outlined.Search,
        title = R.string.switch_to_bing_search,
        checked = switchToBingSearch
    ) {
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        switchToBingSearch = it
        settingsViewModel.update(settingsViewModel.settings.value.copy(switchToBingSearch = it))
    }

    // edit home
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Column(
            Modifier
                .height(dimensionResource(R.dimen.height_setting_row))
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    navController.navigate(SCR_EDIT_HOME)
                }, verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = stringResource(R.string.customize_home_page)
                    )
                    IconAndTextPadding()
                    Text(stringResource(R.string.customize_home_page))
                }
                SpacerPadding()
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
                )
                SpacerPadding()
            }
        }
    }
}

@Composable
private fun Bottom(navController: NavHostController) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current

    // source code
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Column(
            Modifier
                .height(dimensionResource(R.dimen.height_setting_row))
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    view.context.openURL(SOURCE_CODE_URL)
                }, verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Code,
                        contentDescription = null
                    )
                    IconAndTextPadding()
                    Text(stringResource(R.string.view_source_code))
                }
                SpacerPadding()
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = stringResource(R.string.view_source_code),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
                )
                SpacerPadding()
            }
        }
    }

    // licenses
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Column(
            Modifier
                .height(dimensionResource(R.dimen.height_setting_row))
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    navController.navigate(SCR_LICENSES)
                }, verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.FileCopy,
                        contentDescription = null
                    )
                    IconAndTextPadding()
                    Text(stringResource(R.string.licenses))
                }
                SpacerPadding()
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
                )
                SpacerPadding()
            }
        }
    }

    // android app settings
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Column(
            Modifier
                .height(dimensionResource(R.dimen.height_setting_row))
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    view.context.openAppDetailSettings()
                }, verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.SettingsApplications,
                        contentDescription = null
                    )
                    IconAndTextPadding()
                    Text(stringResource(R.string.app_info))
                }
                SpacerPadding()
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
                )
                SpacerPadding()
            }
        }
    }
}