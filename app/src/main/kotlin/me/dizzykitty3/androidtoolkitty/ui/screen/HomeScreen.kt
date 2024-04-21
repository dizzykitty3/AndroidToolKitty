package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.res.dimensionResource
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
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomBottomPadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCardSpacePadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomOneHandedModePadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomTopPadding
import me.dizzykitty3.androidtoolkitty.ui.card.AndroidVersionCard
import me.dizzykitty3.androidtoolkitty.ui.card.AppMarketCard
import me.dizzykitty3.androidtoolkitty.ui.card.BluetoothDeviceCard
import me.dizzykitty3.androidtoolkitty.ui.card.ClipboardCard
import me.dizzykitty3.androidtoolkitty.ui.card.Greeting
import me.dizzykitty3.androidtoolkitty.ui.card.LuckyWheelCard
import me.dizzykitty3.androidtoolkitty.ui.card.MapsCard
import me.dizzykitty3.androidtoolkitty.ui.card.SysSettingCard
import me.dizzykitty3.androidtoolkitty.ui.card.UnicodeCard
import me.dizzykitty3.androidtoolkitty.ui.card.UrlCard
import me.dizzykitty3.androidtoolkitty.ui.card.VolumeCard
import me.dizzykitty3.androidtoolkitty.ui.card.YearProgressCard
import java.util.Locale

@Composable
fun HomeScreen(navController: NavHostController) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card_content)
    val settingsSharedPref = remember { SettingsSharedPref }
    val locale = Locale.getDefault().toString()
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

    LazyColumn(
        modifier = Modifier.padding(
            start = cardPadding,
            end = cardPadding
        )
    ) {
        item { CustomTopPadding() }
        item { GreetingAndSetting(navController) }
        item { CustomCardSpacePadding() }
        if (settingsSharedPref.oneHandedMode) item { CustomOneHandedModePadding() }
        if (!(locale.contains(Regex("en|Hans|zh_CN|zh_SG")))) item {
            CustomTip(
                formattedMessage = stringResource(
                    R.string.no_translation,
                    locale
                )
            )
        }
        cardMapping.forEach { (cardName, isShow) ->
            if (isShow) {
                when (cardName) {
                    CARD_1 -> item { YearProgressCard() }
                    CARD_2 -> item { VolumeCard() }
                    CARD_3 -> item { ClipboardCard() }
                    CARD_4 -> item { UrlCard() }
                    CARD_6 -> item { SysSettingCard() }
                    CARD_7 -> item { UnicodeCard() }
                    CARD_8 -> item { MapsCard() }
                    CARD_9 -> item { AppMarketCard() }
                    CARD_10 -> item { AndroidVersionCard() }
                    CARD_11 -> item { LuckyWheelCard() }
                    CARD_12 -> item { BluetoothDeviceCard(navController) }
                }
            }
        }
        item { CustomBottomPadding() }
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