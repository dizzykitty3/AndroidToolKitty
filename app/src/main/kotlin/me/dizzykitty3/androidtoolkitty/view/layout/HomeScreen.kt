package me.dizzykitty3.androidtoolkitty.view.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomBottomPadding
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
        val isOneHandedMode = SettingsViewModel().getIsOneHandedMode(c)

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

        if (isOneHandedMode) {
            item { CustomOneHandedMode() }
        }

        val isShowYearProgressCard =
            SettingsViewModel().getCardExpandedState(c, "card_year_progress")
        val isShowVolumeCard = SettingsViewModel().getCardExpandedState(c, "card_volume")
        val isShowClipboardCard = SettingsViewModel().getCardExpandedState(c, "card_clipboard")
        val isShowUrlCard = SettingsViewModel().getCardExpandedState(c, "card_url")
        val isShowSocialMediaProfileCard =
            SettingsViewModel().getCardExpandedState(c, "card_social_media_profile")
        val isShowAndroidSystemSettingsCard =
            SettingsViewModel().getCardExpandedState(c, "card_android_system_settings")
        val isShowUnicodeCard = SettingsViewModel().getCardExpandedState(c, "card_unicode")
        val isShowGoogleMapsCard = SettingsViewModel().getCardExpandedState(c, "card_google_maps")
        val isShowOpenAppOnGooglePlayCard =
            SettingsViewModel().getCardExpandedState(c, "card_open_app_on_google_play")
        val isShowAndroidVersionsCard =
            SettingsViewModel().getCardExpandedState(c, "card_android_versions")

        if (isShowYearProgressCard) {
            item { YearProgressCard() }
            item { CustomCardSpacePadding() }
        }

        if (isShowVolumeCard) {
            item { VolumeCard() }
            item { CustomCardSpacePadding() }
        }

        if (isShowClipboardCard) {
            item { ClipboardCard() }
            item { CustomCardSpacePadding() }
        }

        if (isShowUrlCard) {
            item { UrlCard() }
            item { CustomCardSpacePadding() }
        }

        if (isShowSocialMediaProfileCard) {
            item { SocialMediaProfileCard() }
            item { CustomCardSpacePadding() }
        }

        if (isShowAndroidSystemSettingsCard) {
            item { SystemSettingsCard() }
            item { CustomCardSpacePadding() }
        }

        if (isShowUnicodeCard) {
            item { UnicodeCard() }
            item { CustomCardSpacePadding() }
        }

        if (isShowGoogleMapsCard) {
            item { GoogleMapsCard() }
            item { CustomCardSpacePadding() }
        }

        if (isShowOpenAppOnGooglePlayCard) {
            item { OpenAppOnPlayStoreCard() }
            item { CustomCardSpacePadding() }
        }

        if (isShowAndroidVersionsCard) {
            item { AndroidVersionsCard() }
            item { CustomCardSpacePadding() }
        }

        // Bottom
        item { CustomBottomPadding() }
    }
}