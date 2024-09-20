package me.dizzykitty3.androidtoolkitty.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BatteryStd
import androidx.compose.material.icons.outlined.MediaBluetoothOn
import androidx.compose.material.icons.outlined.NetworkCell
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.CARD_1
import me.dizzykitty3.androidtoolkitty.CARD_10
import me.dizzykitty3.androidtoolkitty.CARD_11
import me.dizzykitty3.androidtoolkitty.CARD_12
import me.dizzykitty3.androidtoolkitty.CARD_2
import me.dizzykitty3.androidtoolkitty.CARD_3
import me.dizzykitty3.androidtoolkitty.CARD_4
import me.dizzykitty3.androidtoolkitty.CARD_5
import me.dizzykitty3.androidtoolkitty.CARD_6
import me.dizzykitty3.androidtoolkitty.CARD_7
import me.dizzykitty3.androidtoolkitty.CARD_8
import me.dizzykitty3.androidtoolkitty.CARD_9
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_SETTINGS
import me.dizzykitty3.androidtoolkitty.S_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.S_POWER_USAGE_SUMMARY
import me.dizzykitty3.androidtoolkitty.S_WIFI
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.CardSpacePadding
import me.dizzykitty3.androidtoolkitty.uicomponents.DevBuildTip
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.BatteryUtil
import me.dizzykitty3.androidtoolkitty.utils.BluetoothUtil.headsetNotConnected
import me.dizzykitty3.androidtoolkitty.utils.BluetoothUtil.isHeadsetConnected
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openSystemSettings
import me.dizzykitty3.androidtoolkitty.utils.NetworkUtil

@Composable
fun Home(settingsViewModel: SettingsViewModel, navController: NavHostController, widthType: Int) {
    if (widthType == 0 || widthType == 1) MobileLayout(settingsViewModel, navController)
    else TabletLayout(settingsViewModel, navController)
}

@Composable
private fun MobileLayout(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    val screenPadding = dimensionResource(R.dimen.padding_screen)
    val debug = BuildConfig.DEBUG
    val hideGreetings = settingsViewModel.settings.value.hideGreetings

    LazyColumn(Modifier.padding(start = screenPadding, end = screenPadding)) {
        item { TopBar(settingsViewModel, navController) }
        if (!hideGreetings) {
            item { CardSpacePadding() }
            item { CardSpacePadding() }
            item { Greeting() }
            item { CardSpacePadding() }
        }
        item { CardSpacePadding() }
        if (debug) item {
            DevBuildTip()
            CardSpacePadding()
        }
        item { HomeCards(settingsViewModel, navController) }
    }
}

@Composable
private fun TabletLayout(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    val largeScreenPadding = dimensionResource(R.dimen.padding_screen_large)
    val debug = BuildConfig.DEBUG

    Column(Modifier.padding(start = largeScreenPadding, end = largeScreenPadding)) {
        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.weight(1f)) { Greeting() }
            Box(Modifier.weight(1f)) { TopBar(settingsViewModel, navController, isTablet = true) }
        }
        SpacerPadding()
        if (debug) DevBuildTip()
        TwoColumnHomeCards(settingsViewModel, navController)
    }
}

@Composable
private fun TopBar(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
    isTablet: Boolean = false
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.weight(1f)) { Status(isTablet) }
        SettingsButton(settingsViewModel, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsButton(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    val haptic = LocalHapticFeedback.current

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            PlainTooltip { Text(stringResource(R.string.settings)) }
        },
        state = rememberTooltipState(),
    ) {
        IconButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                navController.navigate(SCR_SETTINGS)
                settingsViewModel.update(settingsViewModel.settings.value.copy(haveOpenedSettings = true))
            },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.settings),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun Status(isTablet: Boolean = false) {
    val batteryLevel = BatteryUtil.batteryLevel()
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current

    Row(Modifier.horizontalScroll(rememberScrollState())) {
        if (isTablet || view.context.headsetNotConnected()) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                Row(Modifier.clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    view.context.openSystemSettings(S_POWER_USAGE_SUMMARY)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.BatteryStd,
                        contentDescription = stringResource(R.string.battery_level),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8F)
                    )
                    SpacerPadding()
                    Text(
                        "$batteryLevel%",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8F)
                    )
                }
            }
            SpacerPadding()
            SpacerPadding()
            NetworkState()
        }

        if (view.context.isHeadsetConnected()) {
            if (isTablet) {
                SpacerPadding()
                SpacerPadding()
            }
            Surface(shape = RoundedCornerShape(8.dp)) {
                Row(Modifier.clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    view.context.openSystemSettings(S_BLUETOOTH)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.MediaBluetoothOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8F)
                    )
                    SpacerPadding()
                    Text(
                        stringResource(R.string.audio_devices_connected),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8F)
                    )
                }
            }
        }
    }
    CardSpacePadding()
}

@Composable
private fun NetworkState() {
    val networkState = remember { NetworkUtil.networkState() }

    when (networkState) {
        NetworkUtil.STATE_CODE_WIFI -> {
            NetworkStateIcon(Icons.Outlined.Wifi, R.string.wifi)
        }

        NetworkUtil.STATE_CODE_MOBILE -> {
            NetworkStateIcon(Icons.Outlined.NetworkCell, R.string.cellular)
        }

        NetworkUtil.STATE_CODE_OFFLINE -> {
            NetworkStateIcon(Icons.Outlined.WifiOff, R.string.offline)
        }

        else -> {
            NetworkStateIcon(Icons.Outlined.QuestionMark, R.string.unknown)
        }
    }
}

@Composable
private fun NetworkStateIcon(imageVector: ImageVector, @StringRes text: Int) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current

    Surface(shape = RoundedCornerShape(8.dp)) {
        Row(Modifier.clickable {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            view.context.openSystemSettings(S_WIFI)
        }) {
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(text),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8F)
            )
            SpacerPadding()
            Text(
                stringResource(text), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8F)
            )
        }
    }
}

@Composable
private fun HomeCards(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    getCardMap(SettingsSharedPref).forEach { (cardName, isShow) ->
        if (isShow) CardContent(settingsViewModel, cardName, navController)
    }
}

@Composable
private fun TwoColumnHomeCards(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController
) {
    val largeCardPadding = dimensionResource(R.dimen.padding_card_space_large)
    val cardMap = getCardMap(SettingsSharedPref)
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = largeCardPadding,
        horizontalArrangement = Arrangement.spacedBy(largeCardPadding),
    ) {
        items(cardMap.toList()) { (cardName, isShow) ->
            if (isShow) CardContent(settingsViewModel, cardName, navController)
        }
    }
}

@Composable
private fun getCardMap(settingsSharedPref: SettingsSharedPref): Map<String, Boolean> = listOf(
    CARD_1, CARD_2, CARD_3, CARD_4, CARD_5, CARD_6,
    CARD_7, CARD_8, CARD_9, CARD_10, CARD_11, CARD_12
).associateWith { card -> settingsSharedPref.getShownState(card) }

@Composable
private fun CardContent(
    settingsViewModel: SettingsViewModel,
    cardName: String,
    navController: NavHostController
) {
    when (cardName) {
        CARD_1 -> YearProgress()
        CARD_2 -> Volume(navController)
        CARD_3 -> Clipboard(settingsViewModel)
        CARD_4 -> Search(navController)
        CARD_5 -> SysSettings(navController)
        CARD_6 -> WheelOfFortune()
        CARD_7 -> BluetoothDevice(navController)
        CARD_8 -> CodesOfCharacters(navController)
        CARD_9 -> Maps()
        CARD_10 -> AndroidVersions(navController)
        CARD_11 -> FontWeight(navController)
        CARD_12 -> ComposeCatalog(navController)
    }
}