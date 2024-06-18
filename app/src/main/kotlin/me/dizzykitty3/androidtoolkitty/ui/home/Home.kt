package me.dizzykitty3.androidtoolkitty.ui.home

import android.view.HapticFeedbackConstants
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
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
import me.dizzykitty3.androidtoolkitty.SETTINGS_SCREEN
import me.dizzykitty3.androidtoolkitty.SETTING_BLUETOOTH
import me.dizzykitty3.androidtoolkitty.SETTING_POWER_USAGE_SUMMARY
import me.dizzykitty3.androidtoolkitty.SETTING_WIFI
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui_components.BottomPadding
import me.dizzykitty3.androidtoolkitty.ui_components.CardSpacePadding
import me.dizzykitty3.androidtoolkitty.ui_components.CustomTip
import me.dizzykitty3.androidtoolkitty.ui_components.OneHandedModePadding
import me.dizzykitty3.androidtoolkitty.ui_components.SpacerPadding
import me.dizzykitty3.androidtoolkitty.ui_components.TopPadding
import me.dizzykitty3.androidtoolkitty.utils.BatteryUtil
import me.dizzykitty3.androidtoolkitty.utils.BluetoothUtil
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil
import me.dizzykitty3.androidtoolkitty.utils.NetworkUtil
import me.dizzykitty3.androidtoolkitty.utils.StringUtil

@Composable
fun Home(navController: NavHostController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    if (screenWidth < 600) MobileLayout(navController)
    else TabletLayout(navController)
}

@Composable
private fun MobileLayout(navController: NavHostController) {
    val settingsSharedPref = remember { SettingsSharedPref }
    val screenPadding = dimensionResource(id = R.dimen.padding_screen)

    LazyColumn(
        modifier = Modifier.padding(
            start = screenPadding,
            end = screenPadding
        )
    ) {
        item { TopPadding() }
        item { TopBar(navController) }
        item { CardSpacePadding() }
        item { CardSpacePadding() }
        item { Greeting() }
        if (settingsSharedPref.oneHandedMode)
            item { OneHandedModePadding() }
        else {
            item { CardSpacePadding() }
            item { CardSpacePadding() }
        }
        item { NoTranslationTip() }
        item { HomeCards(navController) }
        item { BottomPadding() }
    }
}

@Composable
private fun TabletLayout(navController: NavHostController) {
    val largeScreenPadding = dimensionResource(id = R.dimen.padding_screen_large)

    Column(
        modifier = Modifier.padding(
            top = largeScreenPadding,
            start = largeScreenPadding,
            end = largeScreenPadding
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) { Greeting() }
            Box(modifier = Modifier.weight(1f)) { TopBar(navController) }
        }
        SpacerPadding()
        NoTranslationTip()
        TwoColumnHomeCards(navController)
    }
}

@Composable
private fun TopBar(navController: NavHostController) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.weight(1f)) { Status() }
        SettingsButton(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsButton(navController: NavHostController) {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            PlainTooltip { Text(text = stringResource(id = R.string.settings)) }
        },
        state = rememberTooltipState(),
    ) {
        IconButton(
            onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                navController.navigate(SETTINGS_SCREEN)
                settingsSharedPref.haveOpenedSettingsScreen = true
            },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.settings),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun Status() {
    val batteryLevel = remember { BatteryUtil.batteryLevel() }
    val view = LocalView.current

    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Row(modifier = Modifier.clickable {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            IntentUtil.openSystemSettings(SETTING_POWER_USAGE_SUMMARY, view.context)
        }
        ) {
            Icon(
                imageVector = Icons.Outlined.BatteryStd,
                contentDescription = stringResource(id = R.string.battery_level),
                tint = MaterialTheme.colorScheme.primary
            )
            SpacerPadding()
            Text(text = "$batteryLevel%")
        }
        SpacerPadding()
        SpacerPadding()
        NetworkState()
        SpacerPadding()
        SpacerPadding()

        if (BluetoothUtil.isHeadsetConnected() || SettingsSharedPref.uiTesting) {
            Row(modifier = Modifier.clickable {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                IntentUtil.openSystemSettings(SETTING_BLUETOOTH, view.context)
            }) {
                Icon(
                    imageVector = Icons.Outlined.MediaBluetoothOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                SpacerPadding()
                Text(text = stringResource(id = R.string.connected))
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
private fun NetworkStateIcon(
    imageVector: ImageVector,
    @StringRes text: Int,
) {
    val view = LocalView.current

    Row(modifier = Modifier.clickable {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
        IntentUtil.openSystemSettings(SETTING_WIFI, view.context)
    }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(id = text),
            tint = MaterialTheme.colorScheme.primary
        )
        SpacerPadding()
        Text(text = stringResource(id = text))
    }
}

@Composable
private fun NoTranslationTip() {
    val languageNotSupport = StringUtil.sysLangNotSupported
    val uiTesting = SettingsSharedPref.uiTesting
    if (languageNotSupport || uiTesting)
        CustomTip(stringResource(R.string.no_translation, StringUtil.sysLocale))
}

@Composable
private fun HomeCards(navController: NavHostController) {
    val cardMap = getCardMap(SettingsSharedPref)
    cardMap.forEach { (cardName, isShow) ->
        if (isShow) CardContent(cardName, navController)
    }
}

@Composable
private fun TwoColumnHomeCards(navController: NavHostController) {
    val largeCardPadding = dimensionResource(id = R.dimen.padding_card_space_large)
    val cardMap = getCardMap(SettingsSharedPref)
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = largeCardPadding,
        horizontalArrangement = Arrangement.spacedBy(largeCardPadding),
    ) {
        items(cardMap.toList()) { (cardName, isShow) ->
            if (isShow) CardContent(cardName, navController)
        }
    }
}

@Composable
private fun getCardMap(settingsSharedPref: SettingsSharedPref): Map<String, Boolean> = listOf(
    CARD_1, CARD_2, CARD_3, CARD_4, CARD_5,
    CARD_6, CARD_7, CARD_8, CARD_9, CARD_10,
    CARD_11, CARD_12
).associateWith { card -> settingsSharedPref.getCardShowedState(card) }

@Composable
private fun CardContent(cardName: String, navController: NavHostController) {
    when (cardName) {
        CARD_1 -> YearProgress()
        CARD_2 -> Volume()
        CARD_3 -> Clipboard()
        CARD_4 -> Webpage()
        CARD_5 -> SysSettings()
        CARD_6 -> WheelOfFortune()
        CARD_7 -> BluetoothDevice(navController)
        CARD_8 -> Unicode()
        CARD_9 -> AppMarket()
        CARD_10 -> Maps()
        CARD_11 -> AndroidVersions()
        CARD_12 -> FontWeight()
    }
}