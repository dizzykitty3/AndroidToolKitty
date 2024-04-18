package me.dizzykitty3.androidtoolkitty.ui.card

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.foundation.util.AudioUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.AudioUtil.setVolume
import me.dizzykitty3.androidtoolkitty.foundation.util.ToastUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeCard() {
    CustomCard(
        icon = Icons.AutoMirrored.Outlined.VolumeUp,
        title = R.string.volume
    ) {
        val context = LocalContext.current

        val settingsSharedPref = remember { SettingsSharedPref }

        val currentVolume = AudioUtil.volume()
        val maxVolume = AudioUtil.maxVolumeIndex()

        val sliderIncrementFivePercent =
            settingsSharedPref.getIsSliderIncrementFivePercent()

        val customVolume = settingsSharedPref.getCustomVolume()
        var mCustomVolume by remember { mutableIntStateOf(customVolume) }

        val customVolumeOptionLabel = settingsSharedPref.getCustomVolumeOptionLabel()
        var mCustomVolumeOptionLabel by remember { mutableStateOf(customVolumeOptionLabel) }

        val options = listOf(
            stringResource(R.string.mute),
            "30%",
            "50%",
            if (mCustomVolumeOptionLabel != "") mCustomVolumeOptionLabel
            else {
                if (mCustomVolume < 0) "+"
                else "${mCustomVolume}%"
            }
        )

        var selectedIndex by remember {
            mutableStateOf(
                when (currentVolume) {
                    0 -> 0
                    (0.3 * maxVolume).toInt() -> 1
                    (0.5 * maxVolume).toInt() -> 2
                    (mCustomVolume * 0.01 * maxVolume).toInt() -> 3
                    else -> null
                }
            )
        }

        var rememberSelectedIndex: Int? = null
        var showVolumeDialog by remember { mutableStateOf(false) }

        Text(text = stringResource(R.string.media_volume))

        CustomSpacerPadding()

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth(),
            space = SegmentedButtonDefaults.BorderWidth
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    onClick = {
                        rememberSelectedIndex = selectedIndex

                        selectedIndex = index
                        when (index) {
                            0 -> {
                                setVolume(0)
                            }

                            1 -> {
                                setVolume(0.3 * maxVolume)
                            }

                            2 -> {
                                setVolume(0.5 * maxVolume)
                            }

                            3 -> {
                                if (mCustomVolume > 0)
                                    setVolume(mCustomVolume * 0.01 * maxVolume)
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
                    Text(text = label.toString())
                }
            }

            if (showVolumeDialog) {
                var newCustomVolume by remember { mutableFloatStateOf(0f) }
                var optionLabel by remember { mutableStateOf("") }

                AlertDialog(
                    onDismissRequest = {
                        // Ignore
                    },
                    title = {
                        Text(text = stringResource(R.string.add_custom_volume))
                    },
                    text = {
                        Column {
                            Slider(
                                value = newCustomVolume,
                                onValueChange = { newCustomVolume = it },
                                valueRange = 0f..100f,
                                steps = if (sliderIncrementFivePercent) 19 else 0
                            )
                            Text(text = "${newCustomVolume.toInt()}% -> ${(newCustomVolume * 0.01 * maxVolume).toInt()}/$maxVolume")
                            CustomSpacerPadding()
                            OutlinedTextField(
                                value = optionLabel,
                                onValueChange = { optionLabel = it },
                                label = { Text(text = stringResource(R.string.label)) },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        settingsSharedPref.setCustomVolume(newCustomVolume.toInt())
                                        mCustomVolume = newCustomVolume.toInt()
                                        if (optionLabel == "") {
                                            settingsSharedPref.setCustomVolumeOptionLabel(
                                                optionLabel
                                            )
                                            mCustomVolumeOptionLabel = optionLabel
                                        }
                                        selectedIndex = 3
                                        setVolume(mCustomVolume * 0.01 * maxVolume)
                                        showVolumeDialog = false
                                    }
                                ),
                                supportingText = { Text(text = stringResource(R.string.you_can_set_a_label_for_it)) }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if ((newCustomVolume * 0.01 * maxVolume).toInt() == 0 && newCustomVolume.toInt() != 0) {
                                    ToastUtil.toast(context.getString(R.string.system_media_volume_levels_limited))
                                    return@Button
                                } else {
                                    settingsSharedPref.setCustomVolume(newCustomVolume.toInt())
                                    mCustomVolume = newCustomVolume.toInt()
                                    if (optionLabel == "") {
                                        settingsSharedPref.setCustomVolumeOptionLabel(optionLabel)
                                        mCustomVolumeOptionLabel = optionLabel
                                    }
                                    selectedIndex = 3
                                    setVolume(mCustomVolume * 0.01 * maxVolume)
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
                                selectedIndex = rememberSelectedIndex
                                showVolumeDialog = false
                            }
                        ) {
                            Text(text = stringResource(android.R.string.cancel))
                        }
                    }
                )
            }
        }

        if (mCustomVolume > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { showVolumeDialog = true },
                    elevation = ButtonDefaults.buttonElevation(1.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    CustomSpacerPadding()
                    Text(text = stringResource(R.string.edit))
                }
            }
        }
    }
}