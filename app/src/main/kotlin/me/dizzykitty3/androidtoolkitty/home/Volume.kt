package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_CUSTOM_VOLUME
import me.dizzykitty3.androidtoolkitty.SCR_VOLUME
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.GradientSmall
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil.setVolume
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import kotlin.math.roundToInt

@Composable
fun Volume(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current
    Card(
        R.string.volume,
        Icons.AutoMirrored.Outlined.VolumeUp,
        true,
        {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_VOLUME)
        }) {
        MediaVolume(navController, isHome = true)
    }
}

@Composable
fun VolumeScreen(navController: NavHostController) {
    Screen(R.string.volume, navController) {
        Card(R.string.media_volume) { MediaVolume(navController, isHome = false) }
        Card(R.string.voice_call_volume) { VoiceCallVolume() }
    }
}

@Composable
private fun MediaVolume(navController: NavHostController, isHome: Boolean) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    val settingsSharedPref = remember { SettingsSharedPref }
    val maxVolume = AudioUtil.maxMediaVolumeIndex
    var mCustomVolume by remember { mutableIntStateOf(settingsSharedPref.customVolume) }
    var mCustomVolumeOptionLabel by remember { mutableStateOf(settingsSharedPref.customVolumeOptionLabel) }
    var mHaveTappedAddButton by remember { mutableStateOf(settingsSharedPref.haveTappedAddButton) }

    val options = listOf(
        stringResource(R.string.off_all_cap),
        "40%",
        "60%",
        if (mCustomVolume < 0) stringResource(R.string.add) else mCustomVolumeOptionLabel
    )

    var selectedIndex by remember {
        mutableStateOf(
            when (AudioUtil.mediaVolume) {
                0 -> 0
                (0.4 * maxVolume).roundToInt() -> 1
                (0.6 * maxVolume).roundToInt() -> 2
                (mCustomVolume * 0.01 * maxVolume).roundToInt() -> 3
                else -> null
            }
        )
    }

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth(),
        space = SegmentedButtonDefaults.BorderWidth
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    selectedIndex = index
                    when (index) {
                        0 -> {
                            view.setVolume(0)
                        }

                        1 -> {
                            view.setVolume(0.4 * maxVolume)
                        }

                        2 -> {
                            view.setVolume(0.6 * maxVolume)
                        }

                        3 -> {
                            mHaveTappedAddButton = true
                            settingsSharedPref.haveTappedAddButton = true
                            if (mCustomVolume > 0)
                                view.setVolume(mCustomVolume * 0.01 * maxVolume)
                            else
                                navController.navigate(SCR_CUSTOM_VOLUME)
                        }
                    }
                },
                selected = index == selectedIndex,
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                colors = SegmentedButtonDefaults.colors()
                    .copy(inactiveContainerColor = MaterialTheme.colorScheme.surfaceContainerLow)
            ) {
                if (label != stringResource(R.string.add)) {
                    Text(label.toString())
                } else if (mHaveTappedAddButton) {
                    Text(label)
                } else {
                    GradientSmall(
                        textToDisplay = label,
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                }
            }
        }
    }

    if (mCustomVolume > 0 && !isHome) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton({
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                navController.navigate(SCR_CUSTOM_VOLUME)
            }) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.edit),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                SpacerPadding()
                Text(stringResource(R.string.edit))
            }
        }
    }
}

@Composable
private fun VoiceCallVolume() {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    OutlinedButton({
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        val index = AudioUtil.maxVoiceCallVolumeIndex
        AudioUtil.setVoiceCallVolume(index)
        if (AudioUtil.voiceCallVolume == index) {
            view.showSnackbar(R.string.volume_changed)
        }
    }) { Text(stringResource(R.string.max_out_voice_call_volume)) }
}