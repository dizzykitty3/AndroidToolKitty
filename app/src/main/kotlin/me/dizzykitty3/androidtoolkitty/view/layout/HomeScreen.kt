package me.dizzykitty3.androidtoolkitty.view.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomBottomPadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCardNoIcon
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCardSpacePadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomOneHandedMode
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomTopPadding
import me.dizzykitty3.androidtoolkitty.view.card.AndroidVersionsCard
import me.dizzykitty3.androidtoolkitty.view.card.ClipboardCard
import me.dizzykitty3.androidtoolkitty.view.card.GoogleMapsCard
import me.dizzykitty3.androidtoolkitty.view.card.GreetingText
import me.dizzykitty3.androidtoolkitty.view.card.OpenAppOnPlayStoreCard
import me.dizzykitty3.androidtoolkitty.view.card.SocialMediaProfileCard
import me.dizzykitty3.androidtoolkitty.view.card.SystemSettingsCard
import me.dizzykitty3.androidtoolkitty.view.card.UnicodeCard
import me.dizzykitty3.androidtoolkitty.view.card.UrlCard
import me.dizzykitty3.androidtoolkitty.view.card.VolumeCard
import me.dizzykitty3.androidtoolkitty.view.card.YearProgressCard
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

private const val CARD_1 = "card_year_progress"
private const val CARD_2 = "card_volume"
private const val CARD_3 = "card_clipboard"
private const val CARD_4 = "card_url"
private const val CARD_5 = "card_social_media_profile"
private const val CARD_6 = "card_android_system_settings"
private const val CARD_7 = "card_unicode"
private const val CARD_8 = "card_google_maps"
private const val CARD_9 = "card_open_app_on_google_play"
private const val CARD_10 = "card_android_versions"

@Composable
fun HomeScreen(navController: NavHostController) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card_content)
    val c = LocalContext.current
    LazyColumn(
        modifier = Modifier.padding(
            start = cardPadding,
            end = cardPadding
        )
    ) {
        // Top
        item { CustomTopPadding() }

        // Contents
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    GreetingText()
                }
                IconButton(
                    onClick = {
                        navController.navigate("SettingsScreen")
                        SettingsViewModel().setHaveOpenedSettingsScreen(c, true)
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        item { CustomCardSpacePadding() }

        val isOneHandedMode = SettingsViewModel().getIsOneHandedMode(c)
        if (isOneHandedMode) {
            item { CustomOneHandedMode() }
        }

        val isShowCard1 = SettingsViewModel().getCardShowedState(c, CARD_1)
        val isShowCard2 = SettingsViewModel().getCardShowedState(c, CARD_2)
        val isShowCard3 = SettingsViewModel().getCardShowedState(c, CARD_3)
        val isShowCard4 = SettingsViewModel().getCardShowedState(c, CARD_4)
        val isShowCard5 = SettingsViewModel().getCardShowedState(c, CARD_5)
        val isShowCard6 = SettingsViewModel().getCardShowedState(c, CARD_6)
        val isShowCard7 = SettingsViewModel().getCardShowedState(c, CARD_7)
        val isShowCard8 = SettingsViewModel().getCardShowedState(c, CARD_8)
        val isShowCard9 = SettingsViewModel().getCardShowedState(c, CARD_9)
        val isShowCard10 = SettingsViewModel().getCardShowedState(c, CARD_10)
        if (isShowCard1) {
            item { YearProgressCard() }
        }
        if (isShowCard2) {
            item { VolumeCard() }
        }
        if (isShowCard3) {
            item { ClipboardCard() }
        }
        if (isShowCard4) {
            item { UrlCard() }
        }
        if (isShowCard5) {
            item { SocialMediaProfileCard() }
        }
        if (isShowCard6) {
            item { SystemSettingsCard() }
        }
        if (isShowCard7) {
            item { UnicodeCard() }
        }
        if (isShowCard8) {
            item { GoogleMapsCard() }
        }
        if (isShowCard9) {
            item { OpenAppOnPlayStoreCard() }
        }
        if (isShowCard10) {
            item { AndroidVersionsCard() }
        }
        item {
            CustomCardNoIcon(title = "test") {
                Button(
                    onClick = {
                        navController.navigate("LuckySpinningWheelScreen")
                    }) {
                    Text(
                        text = "Lucky spinning wheel"
                    )
                }
            }
        }

        // Bottom
        item { CustomBottomPadding() }
    }
}