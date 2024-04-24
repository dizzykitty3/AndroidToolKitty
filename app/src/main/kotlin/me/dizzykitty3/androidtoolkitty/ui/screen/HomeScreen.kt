package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_1
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_10
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_11
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_12
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_2
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_3
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_4
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_6
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_7
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_8
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_9
import me.dizzykitty3.androidtoolkitty.foundation.const.SETTINGS_SCREEN
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCardSpacePadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomOneHandedModePadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomScreen
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.foundation.util.BatteryUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.NetworkUtil
import me.dizzykitty3.androidtoolkitty.ui.card.AndroidVersionCard
import me.dizzykitty3.androidtoolkitty.ui.card.AppMarketCard
import me.dizzykitty3.androidtoolkitty.ui.card.BluetoothDeviceCard
import me.dizzykitty3.androidtoolkitty.ui.card.ClipboardCard
import me.dizzykitty3.androidtoolkitty.ui.card.Greeting
import me.dizzykitty3.androidtoolkitty.ui.card.MapsCard
import me.dizzykitty3.androidtoolkitty.ui.card.SysSettingCard
import me.dizzykitty3.androidtoolkitty.ui.card.UnicodeCard
import me.dizzykitty3.androidtoolkitty.ui.card.UrlCard
import me.dizzykitty3.androidtoolkitty.ui.card.VolumeCard
import me.dizzykitty3.androidtoolkitty.ui.card.WheelOfFortuneCard
import me.dizzykitty3.androidtoolkitty.ui.card.YearProgressCard
import java.util.Locale

@Composable
fun HomeScreen(navController: NavHostController) {
    val settingsSharedPref = remember { SettingsSharedPref }

    CustomScreen {
        GreetingAndSetting(navController)

        CustomCardSpacePadding()

        if (settingsSharedPref.oneHandedMode) CustomOneHandedModePadding()

        val locale = Locale.getDefault().toString()
        if (!(locale.contains(Regex("en|Hans|zh_CN|zh_SG")))) CustomTip(
            formattedMessage = stringResource(
                R.string.no_translation,
                locale
            )
        )

        BatteryAndNetwork()

        val cardMapping = mapOf(
            CARD_1 to settingsSharedPref.getCardShowedState(CARD_1),
            CARD_2 to settingsSharedPref.getCardShowedState(CARD_2),
            CARD_3 to settingsSharedPref.getCardShowedState(CARD_3),
            CARD_4 to settingsSharedPref.getCardShowedState(CARD_4),
            CARD_6 to settingsSharedPref.getCardShowedState(CARD_6),
            CARD_7 to settingsSharedPref.getCardShowedState(CARD_7),
            CARD_8 to settingsSharedPref.getCardShowedState(CARD_8),
            CARD_9 to settingsSharedPref.getCardShowedState(CARD_9),
            CARD_10 to settingsSharedPref.getCardShowedState(CARD_10),
            CARD_11 to settingsSharedPref.getCardShowedState(CARD_11),
            CARD_12 to settingsSharedPref.getCardShowedState(CARD_12)
        )

        cardMapping.forEach { (cardName, isShow) ->
            if (isShow) {
                when (cardName) {
                    CARD_1 -> YearProgressCard()
                    CARD_2 -> VolumeCard()
                    CARD_3 -> ClipboardCard()
                    CARD_4 -> UrlCard()
                    CARD_6 -> SysSettingCard()
                    CARD_7 -> UnicodeCard()
                    CARD_8 -> MapsCard()
                    CARD_9 -> AppMarketCard()
                    CARD_10 -> AndroidVersionCard()
                    CARD_11 -> WheelOfFortuneCard()
                    CARD_12 -> BluetoothDeviceCard(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingAndSetting(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val settingsSharedPref = remember { SettingsSharedPref }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Greeting()
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
                    settingsSharedPref.openedSettingsScreen = true
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
fun BatteryAndNetwork() {
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
    CustomSpacerPadding()
}

@Composable
fun NetworkState() {
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
fun NetworkStateIcon(
    imageVector: ImageVector,
    id: Int
) {
    Icon(
        imageVector = imageVector,
        contentDescription = stringResource(id = id),
        tint = MaterialTheme.colorScheme.primary
    )
    CustomSpacerPadding()
    Text(text = stringResource(id = id))
}