package me.dizzykitty3.androidtoolkitty.ui.home_screen

import android.view.HapticFeedbackConstants
import android.view.View
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui_components.ClearInput
import me.dizzykitty3.androidtoolkitty.ui_components.CustomCard
import me.dizzykitty3.androidtoolkitty.ui_components.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.ui_components.GradientSmall
import me.dizzykitty3.androidtoolkitty.ui_components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui_components.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil

private const val ADD = "+ Add"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeCard() {
    CustomCard(
        icon = Icons.AutoMirrored.Outlined.VolumeUp,
        titleRes = R.string.volume
    ) {
        val view = LocalView.current
        val settingsSharedPref = remember { SettingsSharedPref }
        val maxVolume = AudioUtil.maxVolumeIndex
        var morePreciseSlider by remember { mutableStateOf(false) }
        var mCustomVolume by remember { mutableIntStateOf(settingsSharedPref.customVolume) }
        var mCustomVolumeOptionLabel by remember { mutableStateOf(settingsSharedPref.customVolumeOptionLabel) }
        var mHaveCustomLabel by remember { mutableStateOf(settingsSharedPref.usingCustomVolumeOptionLabel) }
        var showVolumeDialog by remember { mutableStateOf(false) }
        val showEditVolumeOption = settingsSharedPref.showEditVolumeOption
        var mHaveTappedAddButton by remember { mutableStateOf(settingsSharedPref.haveTappedAddButton) }

        val options = listOf(
            stringResource(id = R.string.off_all_cap),
            "40%",
            "60%",
            if (mCustomVolume < 0) ADD else mCustomVolumeOptionLabel
        )

        var selectedIndex by remember {
            mutableStateOf(
                when (AudioUtil.volume) {
                    0 -> 0
                    (0.4 * maxVolume).toInt() -> 1
                    (0.6 * maxVolume).toInt() -> 2
                    (mCustomVolume * 0.01 * maxVolume).toInt() -> 3
                    else -> null
                }
            )
        }

        Text(text = stringResource(R.string.media_volume))

        SpacerPadding()

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth(),
            space = SegmentedButtonDefaults.BorderWidth
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    onClick = {
                        selectedIndex = index
                        when (index) {
                            0 -> {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                setVolume(view, 0)
                            }

                            1 -> {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                setVolume(view, 0.4 * maxVolume)
                            }

                            2 -> {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                setVolume(view, 0.6 * maxVolume)
                            }

                            3 -> {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                mHaveTappedAddButton = true
                                if (mCustomVolume > 0)
                                    setVolume(view, mCustomVolume * 0.01 * maxVolume)
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
                    if (label != ADD) {
                        Text(text = label.toString())
                    } else if (mHaveTappedAddButton) {
                        Text(text = label.toString())
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
                        // Ignore
                    },
                    title = {
                        if (settingsSharedPref.addedCustomVolume) {
                            Text(text = stringResource(id = R.string.edit))
                        } else {
                            Text(text = stringResource(R.string.add_custom_volume))
                        }
                    },
                    text = {
                        Column {
                            Slider(
                                value = newCustomVolume,
                                onValueChange = {
                                    newCustomVolume = it
                                    if (mCustomVolume < 0 || (!mHaveCustomLabel))
                                        optionLabel = "${it.toInt()}%"
                                },
                                valueRange = 0f..100f,
                                steps = if (morePreciseSlider) 0 else 9
                            )
                            Text(text = "${newCustomVolume.toInt()}% -> ${(newCustomVolume * 0.01 * maxVolume).toInt()}/$maxVolume")
                            SpacerPadding()
                            OutlinedTextField(
                                value = optionLabel,
                                onValueChange = {
                                    optionLabel = it
                                    mHaveCustomLabel = true
                                },
                                label = { Text(text = stringResource(R.string.label_optional)) },
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
                                        setVolume(view, mCustomVolume * 0.01 * maxVolume)
                                        showVolumeDialog = false
                                    }
                                ),
                                trailingIcon = {
                                    ClearInput(text = optionLabel) {
                                        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                        optionLabel = ""
                                        mHaveCustomLabel = true
                                    }
                                }
                            )
                            GroupDivider()
                            CustomSwitchRow(
                                textRes = R.string.more_precise_slider,
                                checked = morePreciseSlider
                            ) {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                morePreciseSlider = it
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                if ((newCustomVolume * 0.01 * maxVolume).toInt() == 0) {
                                    if (newCustomVolume.toInt() != 0) SnackbarUtil.show(
                                        view,
                                        R.string.system_media_volume_levels_limited
                                    )
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
                                    setVolume(view, mCustomVolume * 0.01 * maxVolume)
                                    showVolumeDialog = false
                                }
                            }
                        ) {
                            Text(text = stringResource(id = android.R.string.ok))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                if (!mHaveCustomLabel) mHaveCustomLabel = false
                                showVolumeDialog = false
                                selectedIndex = when (AudioUtil.volume) {
                                    0 -> 0
                                    (0.4 * maxVolume).toInt() -> 1
                                    (0.6 * maxVolume).toInt() -> 2
                                    (mCustomVolume * 0.01 * maxVolume).toInt() -> 3
                                    else -> null
                                }
                            }
                        ) {
                            Text(text = stringResource(android.R.string.cancel))
                        }
                    }
                )
            }
        }

        if (mCustomVolume > 0 && showEditVolumeOption) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                        showVolumeDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    SpacerPadding()
                    Text(text = stringResource(R.string.edit))
                }
            }
        }
    }
}

private fun setVolume(view: View, volume: Int) {
    AudioUtil.setVolume(volume)
    SnackbarUtil.show(view, R.string.volume_changed)
}

private fun setVolume(view: View, volume: Double) {
    AudioUtil.setVolume(volume)
    SnackbarUtil.show(view, R.string.volume_changed)
}