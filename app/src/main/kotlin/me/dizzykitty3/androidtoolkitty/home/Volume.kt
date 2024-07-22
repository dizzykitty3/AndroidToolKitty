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
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.ADD
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.ClearInput
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.uicomponents.GradientSmall
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil.setVolume
import me.dizzykitty3.androidtoolkitty.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Volume() {
    Card(R.string.volume, Icons.AutoMirrored.Outlined.VolumeUp) {
        val view = LocalView.current
        val settingsSharedPref = remember { SettingsSharedPref }
        val maxVolume = AudioUtil.maxMediaVolumeIndex
        var morePreciseSlider by remember { mutableStateOf(false) }
        var mCustomVolume by remember { mutableIntStateOf(settingsSharedPref.customVolume) }
        var mCustomVolumeOptionLabel by remember { mutableStateOf(settingsSharedPref.customVolumeOptionLabel) }
        var mHaveCustomLabel by remember { mutableStateOf(settingsSharedPref.usingCustomVolumeOptionLabel) }
        var showVolumeDialog by remember { mutableStateOf(false) }
        var mHaveTappedAddButton by remember { mutableStateOf(settingsSharedPref.haveTappedAddButton) }

        val options = listOf(
            stringResource(id = R.string.off_all_cap),
            "40%",
            "60%",
            if (mCustomVolume < 0) ADD else mCustomVolumeOptionLabel
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

        Text(stringResource(R.string.media_volume))

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
                                view.hapticFeedback()
                                view.setVolume(0)
                            }

                            1 -> {
                                view.hapticFeedback()
                                view.setVolume(0.4 * maxVolume)
                            }

                            2 -> {
                                view.hapticFeedback()
                                view.setVolume(0.6 * maxVolume)
                            }

                            3 -> {
                                view.hapticFeedback()
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
                    if (label != ADD) {
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
                    title = {
                        if (settingsSharedPref.addedCustomVolume) {
                            Text(stringResource(R.string.edit))
                        } else {
                            Text(stringResource(R.string.add_custom_volume))
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
                                        view.hapticFeedback()
                                        optionLabel = ""
                                        mHaveCustomLabel = true
                                    }
                                }
                            )
                            GroupDivider()
                            CustomSwitchRow(R.string.more_precise_slider, morePreciseSlider) {
                                view.hapticFeedback()
                                morePreciseSlider = it
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            {
                                view.hapticFeedback()
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
                        ) {
                            Text(stringResource(android.R.string.ok))
                        }
                    },
                    dismissButton = {
                        TextButton({
                            view.hapticFeedback()
                            if (!mHaveCustomLabel) mHaveCustomLabel = false
                            showVolumeDialog = false
                            selectedIndex = when (AudioUtil.mediaVolume) {
                                0 -> 0
                                (0.4 * maxVolume).toInt() -> 1
                                (0.6 * maxVolume).toInt() -> 2
                                (mCustomVolume * 0.01 * maxVolume).toInt() -> 3
                                else -> null
                            }
                        }) {
                            Text(stringResource(android.R.string.cancel))
                        }
                    }
                )
            }
        }

        if (mCustomVolume > 0) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton({
                    view.hapticFeedback()
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
}