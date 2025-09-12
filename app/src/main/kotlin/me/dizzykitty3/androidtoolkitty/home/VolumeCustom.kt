package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.ClearInput
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
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

    Screen(
        screenTitle = if (settingsSharedPref.addedCustomVolume) R.string.edit_custom_volume else R.string.save_custom_volume,
        navController = navController
    ) {
        Card(R.string.edit) {
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

            SpacerPadding()
            SpacerPadding()

            Row(modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.weight(1F)) {
                    TextButton(onClick = { morePreciseSlider = !morePreciseSlider }) {
                        Text(
                            text = stringResource(R.string.precise_switch),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Row {
                    Button(
                        {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            if ((newCustomVolume * 0.01 * maxVolume).roundToInt() == 0) {
                                if (newCustomVolume.roundToInt() != 0) view.showSnackbar(R.string.volume_steps_limited)
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
                    ) { Text(stringResource(R.string.save)) }
                }
            }
        }
    }
}