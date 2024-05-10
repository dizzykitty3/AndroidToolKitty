package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.annotation.StringRes
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BatteryStd
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomBottomPadding
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCardSpacePadding
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomOneHandedModePadding
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomTip
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomTopPadding
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_0
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_1
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_10
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_11
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_12
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_2
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_3
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_4
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_5
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_6
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_7
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_8
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_9
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTINGS_SCREEN
import me.dizzykitty3.androidtoolkitty.foundation.util.BatteryUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.NetworkUtil
import me.dizzykitty3.androidtoolkitty.ui.card.AndroidVersionCard
import me.dizzykitty3.androidtoolkitty.ui.card.AppMarketCard
import me.dizzykitty3.androidtoolkitty.ui.card.BluetoothDeviceCard
import me.dizzykitty3.androidtoolkitty.ui.card.ClipboardCard
import me.dizzykitty3.androidtoolkitty.ui.card.FontWeightCard
import me.dizzykitty3.androidtoolkitty.ui.card.Greeting
import me.dizzykitty3.androidtoolkitty.ui.card.MapsCard
import me.dizzykitty3.androidtoolkitty.ui.card.SysSettingCard
import me.dizzykitty3.androidtoolkitty.ui.card.TestCard
import me.dizzykitty3.androidtoolkitty.ui.card.UnicodeCard
import me.dizzykitty3.androidtoolkitty.ui.card.VolumeCard
import me.dizzykitty3.androidtoolkitty.ui.card.WebpageCard
import me.dizzykitty3.androidtoolkitty.ui.card.WheelOfFortuneCard
import me.dizzykitty3.androidtoolkitty.ui.card.YearProgressCard
import java.util.Locale

/**
 * @see me.dizzykitty3.androidtoolkitty.ui.card.EditHomeCard
 */
@Composable
fun HomeScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    if (screenWidth < 600) {  // 假设宽度小于600dp为手机布局
        MobileLayout(navController)
    } else {
        TabletLayout(navController)
    }
}

@Composable
fun MobileLayout(navController: NavHostController) {
    val settingsSharedPref = remember { SettingsSharedPref }
    val cardPadding = dimensionResource(id = R.dimen.padding_card_content)

    LazyColumn(
        modifier = Modifier.padding(
            start = cardPadding,
            end = cardPadding
        )
    ) {
        // Status
        item { CustomTopPadding() }
        item { BatteryNetworkAndSetting(navController) }
        item { CustomCardSpacePadding() }
        item { CustomCardSpacePadding() }

        // Greeting
        item { Greeting() }
        if (settingsSharedPref.oneHandedMode)
            item { CustomOneHandedModePadding() }
        else {
            item { CustomCardSpacePadding() }
            item { CustomCardSpacePadding() }
        }

        // Contents
        val locale = Locale.getDefault().toString()
        if (!(locale.contains(Regex("en|Hans|zh_CN|zh_SG")))) item { NoTranslationTip(locale) }
        item { HomeCards(navController) }
        item { CustomBottomPadding() }
    }
}

@Composable
fun TabletLayout(navController: NavHostController) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card_content_large)

    Column(modifier = Modifier.padding(cardPadding)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // 使用 Box 控制 Greeting 的布局
            Box(modifier = Modifier.weight(1f)) {
                Greeting()
            }

            // 使用 Box 控制 BatteryNetworkAndSetting 的布局
            Box(modifier = Modifier.weight(1f)) {
                BatteryNetworkAndSetting(navController)
            }
        }

        // 使用两列布局显示卡片
        TwoColumnHomeCards(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BatteryNetworkAndSetting(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val settingsSharedPref = remember { SettingsSharedPref }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            BatteryAndNetwork()
        }

        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = {
                PlainTooltip {
                    Text(text = stringResource(id = R.string.settings))
                }
            },
            state = rememberTooltipState(),
        ) {
            IconButton(
                onClick = {
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
}

@Composable
private fun BatteryAndNetwork() {
    val batteryLevel = remember { BatteryUtil.batteryLevel() }

    Row {
        Icon(
            imageVector = Icons.Outlined.BatteryStd,
            contentDescription = stringResource(id = R.string.battery_level),
            tint = MaterialTheme.colorScheme.primary
        )
        CustomSpacerPadding()
        Text(text = "$batteryLevel%")

        CustomSpacerPadding()
        CustomSpacerPadding()

        NetworkState()
    }
    CustomCardSpacePadding()
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
    @StringRes id: Int
) {
    Icon(
        imageVector = imageVector,
        contentDescription = stringResource(id = id),
        tint = MaterialTheme.colorScheme.primary
    )
    CustomSpacerPadding()
    Text(text = stringResource(id = id))
}

@Composable
private fun NoTranslationTip(locale: String) {
    CustomTip(
        formattedMessage = stringResource(
            R.string.no_translation,
            locale
        )
    )
}

@Composable
private fun HomeCards(navController: NavHostController) {
    val settingsSharedPref = remember { SettingsSharedPref }
    val cardMapping = getCardMapping(settingsSharedPref)

    cardMapping.forEach { (cardName, isShow) ->
        if (isShow) {
            CardContent(cardName, navController)
        }
    }
}

@Composable
private fun TwoColumnHomeCards(navController: NavHostController) {
    val settingsSharedPref = remember { SettingsSharedPref }
    val cardPadding = dimensionResource(id = R.dimen.padding_card_content_large)
    val cardMapping = getCardMapping(settingsSharedPref)

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = cardPadding,
        horizontalArrangement = Arrangement.spacedBy(cardPadding),
    ) {
        items(cardMapping.toList()) { (cardName, isShow) ->
            if (isShow) {
                CardContent(cardName, navController)
            }
        }
    }
}

@Suppress("KotlinConstantConditions")
@Composable
private fun getCardMapping(settingsSharedPref: SettingsSharedPref): Map<String, Boolean> {
    val debugBuild = BuildConfig.BUILD_TYPE == "debug"
    return if (debugBuild) {
        listOf(
            CARD_0,
            CARD_1, CARD_2, CARD_3, CARD_4, CARD_5,
            CARD_6, CARD_7, CARD_8, CARD_9, CARD_10,
            CARD_11, CARD_12
        ).associateWith { card -> settingsSharedPref.getCardShowedState(card) }
    } else {
        listOf(
            CARD_1, CARD_2, CARD_3, CARD_4, CARD_5,
            CARD_6, CARD_7, CARD_8, CARD_9, CARD_10,
            CARD_11, CARD_12
        ).associateWith { card -> settingsSharedPref.getCardShowedState(card) }
    }
}

@Composable
private fun CardContent(cardName: String, navController: NavHostController) {
    when (cardName) {
        CARD_0 -> TestCard(navController)
        CARD_1 -> YearProgressCard()
        CARD_2 -> VolumeCard()
        CARD_3 -> ClipboardCard()
        CARD_4 -> WebpageCard()
        CARD_5 -> SysSettingCard()
        CARD_6 -> WheelOfFortuneCard()
        CARD_7 -> BluetoothDeviceCard(navController)
        CARD_8 -> UnicodeCard()
        CARD_9 -> AppMarketCard()
        CARD_10 -> MapsCard()
        CARD_11 -> AndroidVersionCard()
        CARD_12 -> FontWeightCard()
    }
}