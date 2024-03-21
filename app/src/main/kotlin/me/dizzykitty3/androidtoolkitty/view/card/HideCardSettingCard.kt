package me.dizzykitty3.androidtoolkitty.view.card

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomStaticCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun HideCardSettingCard() {
    val c = LocalContext.current
    CustomStaticCard(
        title = "Hide card setting"
    ) {
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
        var mIsShowYearProgressCard by remember { mutableStateOf(isShowYearProgressCard) }
        var mIsShowVolumeCard by remember { mutableStateOf(isShowVolumeCard) }
        var mIsShowClipboardCard by remember { mutableStateOf(isShowClipboardCard) }
        var mIsShowUrlCard by remember { mutableStateOf(isShowUrlCard) }
        var mIsShowSocialMediaProfileCard by remember { mutableStateOf(isShowSocialMediaProfileCard) }
        var mIsShowAndroidSystemSettingsCard by remember {
            mutableStateOf(
                isShowAndroidSystemSettingsCard
            )
        }
        var mIsShowUnicodeCard by remember { mutableStateOf(isShowUnicodeCard) }
        var mIsShowGoogleMapsCard by remember { mutableStateOf(isShowGoogleMapsCard) }
        var mIsShowOpenAppOnGooglePlayCard by remember {
            mutableStateOf(
                isShowOpenAppOnGooglePlayCard
            )
        }
        var mIsShowAndroidVersionsCard by remember { mutableStateOf(isShowAndroidVersionsCard) }
        CustomTip(
            text = ">>>UNDER DEVELOPMENT<<<"
        )
        CustomSpacerPadding()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowYearProgressCard = !mIsShowYearProgressCard
                SettingsViewModel().saveCardExpandedState(
                    c,
                    "card_year_progress",
                    mIsShowYearProgressCard
                )
            }
        ) {
            Text(
                text = c.getString(R.string.year_progress)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowYearProgressCard,
                onCheckedChange = {
                    mIsShowYearProgressCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_year_progress", it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowVolumeCard = !mIsShowVolumeCard
                SettingsViewModel().saveCardExpandedState(c, "card_volume", mIsShowYearProgressCard)
            }
        ) {
            Text(
                text = c.getString(R.string.volume)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowVolumeCard,
                onCheckedChange = {
                    mIsShowVolumeCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_volume", it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowClipboardCard = !mIsShowClipboardCard
                SettingsViewModel().saveCardExpandedState(
                    c,
                    "card_clipboard",
                    mIsShowYearProgressCard
                )
            }
        ) {
            Text(
                text = c.getString(R.string.clipboard)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowClipboardCard,
                onCheckedChange = {
                    mIsShowClipboardCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_clipboard", it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowUrlCard = !mIsShowUrlCard
                SettingsViewModel().saveCardExpandedState(c, "card_url", mIsShowYearProgressCard)
            }
        ) {
            Text(
                text = c.getString(R.string.url)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowUrlCard,
                onCheckedChange = {
                    mIsShowUrlCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_url", it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowSocialMediaProfileCard = !mIsShowSocialMediaProfileCard
                SettingsViewModel().saveCardExpandedState(
                    c,
                    "card_social_media_profile",
                    mIsShowYearProgressCard
                )
            }
        ) {
            Text(
                text = c.getString(R.string.social_media_profile)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowSocialMediaProfileCard,
                onCheckedChange = {
                    mIsShowSocialMediaProfileCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_social_media_profile", it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowAndroidSystemSettingsCard = !mIsShowAndroidSystemSettingsCard
                SettingsViewModel().saveCardExpandedState(
                    c,
                    "card_android_system_settings",
                    mIsShowYearProgressCard
                )
            }
        ) {
            Text(
                text = c.getString(R.string.android_system_settings)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowAndroidSystemSettingsCard,
                onCheckedChange = {
                    mIsShowAndroidSystemSettingsCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_android_system_settings", it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowUnicodeCard = !mIsShowUnicodeCard
                SettingsViewModel().saveCardExpandedState(
                    c,
                    "card_unicode",
                    mIsShowYearProgressCard
                )
            }
        ) {
            Text(
                text = c.getString(R.string.unicode)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowUnicodeCard,
                onCheckedChange = {
                    mIsShowUnicodeCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_unicode", it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowGoogleMapsCard = !mIsShowGoogleMapsCard
                SettingsViewModel().saveCardExpandedState(
                    c,
                    "card_google_maps",
                    mIsShowYearProgressCard
                )
            }
        ) {
            Text(
                text = c.getString(R.string.google_maps)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowGoogleMapsCard,
                onCheckedChange = {
                    mIsShowGoogleMapsCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_google_maps", it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowOpenAppOnGooglePlayCard = !mIsShowOpenAppOnGooglePlayCard
                SettingsViewModel().saveCardExpandedState(
                    c,
                    "card_open_app_on_google_play",
                    mIsShowYearProgressCard
                )
            }
        ) {
            Text(
                text = c.getString(R.string.open_app_on_google_play)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowOpenAppOnGooglePlayCard,
                onCheckedChange = {
                    mIsShowOpenAppOnGooglePlayCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_open_app_on_google_play", it)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mIsShowAndroidVersionsCard = !mIsShowAndroidVersionsCard
                SettingsViewModel().saveCardExpandedState(
                    c,
                    "card_android_versions",
                    mIsShowYearProgressCard
                )
            }
        ) {
            Text(
                text = c.getString(R.string.android_versions)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = mIsShowAndroidVersionsCard,
                onCheckedChange = {
                    mIsShowAndroidVersionsCard = it
                    SettingsViewModel().saveCardExpandedState(c, "card_android_versions", it)
                }
            )
        }
        CustomSpacerPadding()
        HorizontalDivider()
        CustomSpacerPadding()
        Button(
            onClick = {
                onClickAllCardsButton(c, false)
                mIsShowYearProgressCard = false
                mIsShowVolumeCard = false
                mIsShowClipboardCard = false
                mIsShowUrlCard = false
                mIsShowSocialMediaProfileCard = false
                mIsShowAndroidSystemSettingsCard = false
                mIsShowUnicodeCard = false
                mIsShowGoogleMapsCard = false
                mIsShowOpenAppOnGooglePlayCard = false
                mIsShowAndroidVersionsCard = false
            }
        ) {
            Text(
                text = c.getString(R.string.hide_all_cards)
            )
        }
        Button(
            onClick = {
                onClickAllCardsButton(c, true)
                mIsShowYearProgressCard = true
                mIsShowVolumeCard = true
                mIsShowClipboardCard = true
                mIsShowUrlCard = true
                mIsShowSocialMediaProfileCard = true
                mIsShowAndroidSystemSettingsCard = true
                mIsShowUnicodeCard = true
                mIsShowGoogleMapsCard = true
                mIsShowOpenAppOnGooglePlayCard = true
                mIsShowAndroidVersionsCard = true
            }
        ) {
            Text(
                text = c.getString(R.string.show_all_cards)
            )
        }
    }
}

private fun onClickAllCardsButton(c: Context, isExpand: Boolean) {
    SettingsViewModel().saveCardExpandedState(c, "card_year_progress", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_volume", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_clipboard", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_url", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_social_media_profile", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_android_system_settings", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_unicode", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_google_maps", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_open_app_on_google_play", isExpand)
    SettingsViewModel().saveCardExpandedState(c, "card_android_versions", isExpand)
}