package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_VOLUME
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.ClearInput
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.uicomponents.GradientSmall
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupTitle
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil.setVolume
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar

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
        MediaVolume(isHome = true)
    }
}

@Composable
fun VolumeScreen() {
    Screen {
        Card(R.string.volume, Icons.AutoMirrored.Outlined.VolumeUp) {
            GroupTitle(R.string.media_volume)
            SpacerPadding()
            MediaVolume(isHome = false)
            GroupDivider()
            VoiceCallVolume()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MediaVolume(isHome: Boolean) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    val settingsSharedPref = remember { SettingsSharedPref }
    val maxVolume = AudioUtil.maxMediaVolumeIndex
    var morePreciseSlider by remember { mutableStateOf(false) }
    var mCustomVolume by remember { mutableIntStateOf(settingsSharedPref.customVolume) }
    var mCustomVolumeOptionLabel by remember { mutableStateOf(settingsSharedPref.customVolumeOptionLabel) }
    var mHaveCustomLabel by remember { mutableStateOf(settingsSharedPref.usingCustomVolumeOptionLabel) }
    var showVolumeDialog by remember { mutableStateOf(false) }
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
                (0.4 * maxVolume).toInt() -> 1
                (0.6 * maxVolume).toInt() -> 2
                (mCustomVolume * 0.01 * maxVolume).toInt() -> 3
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
                            if (mCustomVolume > 0)
                                view.setVolume(mCustomVolume * 0.01 * maxVolume)
                            else
                                showVolumeDialog = true
                        }
                    }
                },
                selected = index == selectedIndex,
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                )
            ) {
                if (label != stringResource(R.string.add)) {
                    Text(label.toString())
                } else if (mHaveTappedAddButton) {
                    Text(label.toString())
                } else {
                    GradientSmall(
                        textToDisplay = label.toString(),
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                }
            }
        }

        if (showVolumeDialog) {
            var newCustomVolume by remember {
                if (mCustomVolume < 0) {
                    mutableFloatStateOf(0f)
                } else {
                    mutableFloatStateOf(mCustomVolume.toFloat())
                }
            }

            var optionLabel by remember {
                if (mCustomVolume < 0) {
                    mutableStateOf("")
                } else {
                    mutableStateOf(mCustomVolumeOptionLabel.toString())
                }
            }

            AlertDialog(
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.VolumeUp,
                        contentDescription = null
                    )
                },
                onDismissRequest = {
                    if (!mHaveCustomLabel) mHaveCustomLabel = false
                    showVolumeDialog = false
                    selectedIndex = when (AudioUtil.mediaVolume) {
                        0 -> 0
                        (0.4 * maxVolume).toInt() -> 1
                        (0.6 * maxVolume).toInt() -> 2
                        (mCustomVolume * 0.01 * maxVolume).toInt() -> 3
                        else -> null
                    }
                },
                title = { Text(stringResource(if (settingsSharedPref.addedCustomVolume) R.string.edit else R.string.add_custom_volume)) },
                text = {
                    Column {
                        Slider(
                            value = newCustomVolume,
                            onValueChange = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                newCustomVolume = it
                                if (mCustomVolume < 0 || (!mHaveCustomLabel))
                                    optionLabel = "${it.toInt()}%"
                            },
                            valueRange = 0f..100f,
                            steps = if (morePreciseSlider) 0 else 9
                        )
                        Text("${newCustomVolume.toInt()}% -> ${(newCustomVolume * 0.01 * maxVolume).toInt()}/$maxVolume")
                        SpacerPadding()
                        OutlinedTextField(
                            value = optionLabel,
                            onValueChange = {
                                optionLabel = it
                                mHaveCustomLabel = true
                            },
                            label = { Text(stringResource(R.string.label_optional)) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    settingsSharedPref.customVolume = newCustomVolume.toInt()
                                    mCustomVolume = newCustomVolume.toInt()
                                    if (mHaveCustomLabel) {
                                        settingsSharedPref.usingCustomVolumeOptionLabel = true
                                    }
                                    settingsSharedPref.customVolumeOptionLabel = optionLabel
                                    mCustomVolumeOptionLabel = optionLabel
                                    selectedIndex = 3
                                    view.setVolume(mCustomVolume * 0.01 * maxVolume)
                                    showVolumeDialog = false
                                }
                            ),
                            trailingIcon = {
                                ClearInput(optionLabel) {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    optionLabel = ""
                                    mHaveCustomLabel = true
                                }
                            }
                        )
                        GroupDivider()
                        CustomSwitchRow(
                            R.string.more_precise_slider,
                            R.string.slider_increment_1_percent,
                            morePreciseSlider
                        ) {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            morePreciseSlider = it
                        }
                    }
                },
                confirmButton = {
                    Button(
                        {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            if ((newCustomVolume * 0.01 * maxVolume).toInt() == 0) {
                                if (newCustomVolume.toInt() != 0) view.showSnackbar(R.string.system_media_volume_levels_limited)
                                return@Button
                            } else {
                                settingsSharedPref.customVolume = newCustomVolume.toInt()
                                mCustomVolume = newCustomVolume.toInt()
                                if (mHaveCustomLabel) {
                                    settingsSharedPref.usingCustomVolumeOptionLabel = true
                                }
                                settingsSharedPref.customVolumeOptionLabel = optionLabel
                                mCustomVolumeOptionLabel = optionLabel
                                selectedIndex = 3
                                view.setVolume(mCustomVolume * 0.01 * maxVolume)
                                showVolumeDialog = false
                            }
                        },
                        elevation = ButtonDefaults.buttonElevation(1.dp)
                    ) { Text(stringResource(android.R.string.ok)) }
                },
                dismissButton = {
                    TextButton({
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        if (!mHaveCustomLabel) mHaveCustomLabel = false
                        showVolumeDialog = false
                        selectedIndex = when (AudioUtil.mediaVolume) {
                            0 -> 0
                            (0.4 * maxVolume).toInt() -> 1
                            (0.6 * maxVolume).toInt() -> 2
                            (mCustomVolume * 0.01 * maxVolume).toInt() -> 3
                            else -> null
                        }
                    }) { Text(stringResource(android.R.string.cancel)) }
                }
            )
        }
    }

    if (mCustomVolume > 0 && !isHome) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton({
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                showVolumeDialog = true
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
    GroupTitle(R.string.voice_call_volume)
    OutlinedButton({
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        val index = AudioUtil.maxVoiceCallVolumeIndex
        AudioUtil.setVoiceCallVolume(index)
        if (AudioUtil.voiceCallVolume == index) {
            view.showSnackbar(R.string.success)
        }
    }) { Text(stringResource(R.string.max_out_voice_call_volume)) }
}