package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.*
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil.setVolume
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import kotlin.math.roundToInt

@Composable
fun CustomVolumeScreen(navController: NavHostController) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    val settingsSharedPref = remember { SettingsSharedPref }
    val maxVolume = AudioUtil.maxMediaVolumeIndex
    var morePreciseSlider by remember { mutableStateOf(false) }
    var mCustomVolume by remember { mutableIntStateOf(settingsSharedPref.customVolume) }
    var mCustomVolumeOptionLabel by remember { mutableStateOf(settingsSharedPref.customVolumeOptionLabel) }
    var mHaveCustomLabel by remember { mutableStateOf(settingsSharedPref.usingCustomVolumeOptionLabel) }

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

    Screen(navController) {
        Card(stringResource(if (settingsSharedPref.addedCustomVolume) R.string.edit_custom_volume else R.string.save_custom_volume)) {
            Slider(
                value = newCustomVolume,
                onValueChange = {
                    if (morePreciseSlider)
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    else haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    newCustomVolume = it
                    if (mCustomVolume < 0 || (!mHaveCustomLabel))
                        optionLabel = "${it.roundToInt()}%"
                },
                valueRange = 0f..100f,
                steps = if (morePreciseSlider) 0 else 9
            )
            Text("${newCustomVolume.roundToInt()}% -> ${(newCustomVolume * 0.01 * maxVolume).roundToInt()}/$maxVolume")
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
                        settingsSharedPref.customVolume = newCustomVolume.roundToInt()
                        mCustomVolume = newCustomVolume.roundToInt()
                        if (mHaveCustomLabel) {
                            settingsSharedPref.usingCustomVolumeOptionLabel = true
                        }
                        settingsSharedPref.customVolumeOptionLabel = optionLabel
                        mCustomVolumeOptionLabel = optionLabel
                        view.setVolume(mCustomVolume * 0.01 * maxVolume)
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

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLow
            ) {
                Column(Modifier.clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    morePreciseSlider = !morePreciseSlider
                }) {
                    SpacerPadding()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(
                            Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(stringResource(R.string.precise_slider))
                                Description(R.string.slider_increment_1_percent)
                            }
                        }
                        SpacerPadding()
                        Switch(
                            checked = morePreciseSlider,
                            onCheckedChange = { morePreciseSlider = it }
                        )
                    }
                    SpacerPadding()
                }
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                Row {
                    Button(
                        {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            if ((newCustomVolume * 0.01 * maxVolume).roundToInt() == 0) {
                                if (newCustomVolume.roundToInt() != 0) view.showSnackbar(R.string.system_media_volume_levels_limited)
                                return@Button
                            } else {
                                settingsSharedPref.customVolume = newCustomVolume.roundToInt()
                                mCustomVolume = newCustomVolume.roundToInt()
                                if (mHaveCustomLabel) {
                                    settingsSharedPref.usingCustomVolumeOptionLabel = true
                                }
                                settingsSharedPref.customVolumeOptionLabel = optionLabel
                                mCustomVolumeOptionLabel = optionLabel
                                view.setVolume(mCustomVolume * 0.01 * maxVolume)
                                navController.popBackStack()
                            }
                        },
                        elevation = ButtonDefaults.buttonElevation(1.dp)
                    ) { Text(stringResource(android.R.string.ok)) }
                }
            }
        }
    }
}