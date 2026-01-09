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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowSizeClass
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
import me.dizzykitty3.androidtoolkitty.S_BATTERY
import me.dizzykitty3.androidtoolkitty.S_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.S_WIFI
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.BottomPadding
import me.dizzykitty3.androidtoolkitty.uicomponents.CardSpacePadding
import me.dizzykitty3.androidtoolkitty.uicomponents.DevBuildTip
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.BatteryUtil
import me.dizzykitty3.androidtoolkitty.utils.BluetoothUtil.headsetNotConnected
import me.dizzykitty3.androidtoolkitty.utils.BluetoothUtil.isHeadsetConnected
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openSystemSettings
import me.dizzykitty3.androidtoolkitty.utils.NetworkUtil

@Composable
fun Home(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    if (currentWindowAdaptiveInfo().windowSizeClass.minWidthDp >= WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
        TabletLayout(settingsViewModel, navController)
    else
        MobileLayout(settingsViewModel, navController)
}

@Composable
private fun MobileLayout(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    val screenPadding = dimensionResource(R.dimen.padding_screen)
    val debug = BuildConfig.DEBUG

    LazyColumn(Modifier.padding(start = screenPadding, end = screenPadding)) {
        item { TopBar(navController) }
        item { CardSpacePadding() }
        item { CardSpacePadding() }
        item { Greeting() }
        item { CardSpacePadding() }
        item { CardSpacePadding() }
        if (debug) item {
            DevBuildTip()
            CardSpacePadding()
            Test()
        }
        item { HomeCards(settingsViewModel, navController) }
        item { BottomPadding() }
    }
}

@Composable
private fun TabletLayout(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    val largeScreenPadding = dimensionResource(R.dimen.padding_screen_large)
    val debug = BuildConfig.DEBUG

    Column(Modifier.padding(start = largeScreenPadding, end = largeScreenPadding)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(1F)) { Greeting() }
            Box(Modifier.weight(1F)) { TopBar(navController, isTablet = true) }
        }
        SpacerPadding()
        if (debug) {
            DevBuildTip()
        }
        TwoColumnHomeCards(settingsViewModel, navController)
    }
}

@Composable
private fun TopBar(navController: NavHostController, isTablet: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.weight(1F)) { Status(isTablet) }
        SettingsButton(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsButton(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current

    IconButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_SETTINGS)
        }
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(R.string.settings),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun Status(isTablet: Boolean = false) {
    val batteryLevel = BatteryUtil.batteryLevel()
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current

    Row(Modifier.horizontalScroll(rememberScrollState())) {
        if (isTablet || view.context.headsetNotConnected()) {
            Surface(
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_shape)),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                Row(Modifier.clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    view.context.openSystemSettings(S_BATTERY)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.BatteryStd,
                        contentDescription = stringResource(R.string.battery),
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
            Surface(
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_shape)),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
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

    Surface(
        shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_shape)),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
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
    getCardMap(SettingsSharedPref).forEach { cardName ->
        CardContent(cardName, settingsViewModel, navController)
    }
}

@Composable
private fun TwoColumnHomeCards(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController
) {
    val cardPadding = dimensionResource(R.dimen.padding_card_space)
    val largeCardPadding = dimensionResource(R.dimen.padding_card_space_large)
    val cardMap = getCardMap(SettingsSharedPref)
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = cardPadding,
        horizontalArrangement = Arrangement.spacedBy(largeCardPadding),
    ) {
        items(cardMap) { cardName ->
            CardContent(cardName, settingsViewModel, navController)
        }
        item { BottomPadding() }
    }
}

@Composable
private fun getCardMap(settingsSharedPref: SettingsSharedPref): List<String> = listOf(
    CARD_1, CARD_2, CARD_3, CARD_4, CARD_5, CARD_6,
    CARD_7, CARD_8, CARD_9, CARD_10, CARD_11, CARD_12
).associateWith { card -> settingsSharedPref.getShownState(card) }.filter { it.value }.keys.toList()

@Composable
private fun CardContent(
    cardName: String,
    settingsViewModel: SettingsViewModel,
    navController: NavHostController
) {
    when (cardName) {
        CARD_1 -> YearProgress()
        CARD_2 -> Volume(navController)
        CARD_3 -> Clipboard()
        CARD_4 -> Search(settingsViewModel, navController)
        CARD_5 -> SysSettings(navController)
        CARD_6 -> WheelOfFortune(navController)
        CARD_7 -> BluetoothDevice(navController)
        CARD_8 -> CodesOfCharacters(navController)
        CARD_9 -> Maps()
        CARD_10 -> FontWeight(navController)
        CARD_11 -> ComposeCatalog(navController)
        CARD_12 -> HapticFeedback(navController)
    }
}