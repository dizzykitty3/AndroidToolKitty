package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCardSpacePadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomOneHandedModePadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomScreen
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.ui.card.AndroidVersionsCard
import me.dizzykitty3.androidtoolkitty.ui.card.AppMarketCard
import me.dizzykitty3.androidtoolkitty.ui.card.BluetoothDevicesCard
import me.dizzykitty3.androidtoolkitty.ui.card.ClipboardCard
import me.dizzykitty3.androidtoolkitty.ui.card.Greeting
import me.dizzykitty3.androidtoolkitty.ui.card.LuckyWheelCard
import me.dizzykitty3.androidtoolkitty.ui.card.MapsCard
import me.dizzykitty3.androidtoolkitty.ui.card.SystemSettingsCard
import me.dizzykitty3.androidtoolkitty.ui.card.UnicodeCard
import me.dizzykitty3.androidtoolkitty.ui.card.UrlCard
import me.dizzykitty3.androidtoolkitty.ui.card.VolumeCard
import me.dizzykitty3.androidtoolkitty.ui.card.YearProgressCard
import java.util.Locale

private const val SETTINGS_SCREEN = "SettingsScreen"
private const val LUCKY_SPINNING_WHEEL_SCREEN = "LuckySpinningWheelScreen"
private const val BLUETOOTH_DEVICES_SCREEN = "BluetoothDevicesScreen"
private const val CARD_1 = "card_year_progress"
private const val CARD_2 = "card_volume"
private const val CARD_3 = "card_clipboard"
private const val CARD_4 = "card_url"
private const val CARD_6 = "card_android_system_settings"
private const val CARD_7 = "card_unicode"
private const val CARD_8 = "card_google_maps"
private const val CARD_9 = "card_open_app_on_google_play"
private const val CARD_10 = "card_android_versions"
private const val CARD_11 = "card_lucky_wheel"
private const val CARD_12 = "card_bluetooth_devices"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val settingsSharedPref = remember { SettingsSharedPref }

    CustomScreen {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                        settingsSharedPref.setHaveOpenedSettingsScreen(true)
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

        CustomCardSpacePadding()

        if (settingsSharedPref.getIsOneHandedMode()) CustomOneHandedModePadding()

        val locale = Locale.getDefault().toString()
        if (!(locale.contains(Regex("en|Hans|zh_CN|zh_SG")))) CustomTip(
            formattedMessage = stringResource(
                R.string.no_translation,
                locale
            )
        )

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
                    CARD_6 -> SystemSettingsCard()
                    CARD_7 -> UnicodeCard()
                    CARD_8 -> MapsCard()
                    CARD_9 -> AppMarketCard()
                    CARD_10 -> AndroidVersionsCard()
                    CARD_11 -> LuckyWheelCard()
                    CARD_12 -> BluetoothDevicesCard()
                }
            }
        }

        CustomCard(title = R.string.test) {
            Button(
                onClick = { navController.navigate(LUCKY_SPINNING_WHEEL_SCREEN) }
            ) {
                Text(text = stringResource(R.string.lucky_spinning_wheel))
            }
            Button(
                onClick = { navController.navigate(BLUETOOTH_DEVICES_SCREEN) }
            ) {
                Text(text = stringResource(id = R.string.bluetooth_devices))
            }
        }
    }
}

@Composable
fun LuckySpinningWheelScreen() {
    CustomScreen {
        LuckyWheelCard()
    }
}

@Composable
fun BluetoothDevicesScreen() {
    CustomScreen {
        BluetoothDevicesCard()
    }
}