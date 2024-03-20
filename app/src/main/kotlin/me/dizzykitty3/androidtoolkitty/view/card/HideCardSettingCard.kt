package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun HideCardSettingCard() {
    val c = LocalContext.current
    CustomStaticCard(
        title = "Hide card setting"
    ) {
        // viewmodel
        var isShowYearProgressCard by remember { mutableStateOf(true) }
        var isShowVolumeCard by remember { mutableStateOf(true) }
        var isShowClipboardCard by remember { mutableStateOf(true) }
        var isShowUrlCard by remember { mutableStateOf(true) }
        var isShowSocialMediaProfileCard by remember { mutableStateOf(true) }
        var isShowAndroidSystemSettingsCard by remember { mutableStateOf(true) }
        var isShowUnicodeCard by remember { mutableStateOf(true) }
        var isShowGoogleMapsCard by remember { mutableStateOf(true) }
        var isShowOpenAppOnGooglePlayCard by remember { mutableStateOf(true) }
        CustomTip(
            text = ">>>UNDER DEVELOPMENT<<<"
        )
        CustomTip(
            text = "UI only"
        )
        CustomSpacerPadding()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isShowYearProgressCard = !isShowYearProgressCard
                // viewmodel
            }
        ) {
            Text(
                text = c.getString(R.string.year_progress)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isShowYearProgressCard,
                onCheckedChange = {
                    isShowYearProgressCard = it
                    // viewmodel
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isShowVolumeCard = !isShowVolumeCard
                // viewmodel
            }
        ) {
            Text(
                text = c.getString(R.string.volume)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isShowVolumeCard,
                onCheckedChange = {
                    isShowVolumeCard = it
                    // viewmodel
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isShowClipboardCard = !isShowClipboardCard
                // viewmodel
            }
        ) {
            Text(
                text = c.getString(R.string.clipboard)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isShowClipboardCard,
                onCheckedChange = {
                    isShowClipboardCard = it
                    // viewmodel
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isShowUrlCard = !isShowUrlCard
                // viewmodel
            }
        ) {
            Text(
                text = c.getString(R.string.url)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isShowUrlCard,
                onCheckedChange = {
                    isShowUrlCard = it
                    // viewmodel
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isShowSocialMediaProfileCard = !isShowSocialMediaProfileCard
                // viewmodel
            }
        ) {
            Text(
                text = c.getString(R.string.social_media_profile)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isShowSocialMediaProfileCard,
                onCheckedChange = {
                    isShowSocialMediaProfileCard = it
                    // viewmodel
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isShowAndroidSystemSettingsCard = !isShowAndroidSystemSettingsCard
                // viewmodel
            }
        ) {
            Text(
                text = c.getString(R.string.android_system_settings)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isShowAndroidSystemSettingsCard,
                onCheckedChange = {
                    isShowAndroidSystemSettingsCard = it
                    // viewmodel
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isShowUnicodeCard = !isShowUnicodeCard
                // viewmodel
            }
        ) {
            Text(
                text = c.getString(R.string.unicode)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isShowUnicodeCard,
                onCheckedChange = {
                    isShowUnicodeCard = it
                    // viewmodel
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isShowGoogleMapsCard = !isShowGoogleMapsCard
                // viewmodel
            }
        ) {
            Text(
                text = c.getString(R.string.google_maps)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isShowGoogleMapsCard,
                onCheckedChange = {
                    isShowGoogleMapsCard = it
                    // viewmodel
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isShowOpenAppOnGooglePlayCard = !isShowOpenAppOnGooglePlayCard
                // viewmodel
            }
        ) {
            Text(
                text = c.getString(R.string.open_app_on_google_play)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isShowOpenAppOnGooglePlayCard,
                onCheckedChange = {
                    isShowOpenAppOnGooglePlayCard = it
                    // viewmodel
                }
            )
        }
    }
}