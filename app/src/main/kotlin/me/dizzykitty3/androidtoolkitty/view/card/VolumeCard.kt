package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.common.util.AudioUtils
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.AutoMirrored.Outlined.VolumeUp,
        title = c.getString(R.string.volume)
    ) {
        val maxVolume = AudioUtils(c).getMaxVolumeIndex()
        val customVolume = SettingsViewModel().getCustomVolume(c)
        var mCustomVolume by remember { mutableIntStateOf(customVolume) }
        val options = listOf(
            "0",
            "40%",
            "60%",
            if (mCustomVolume == -1) "+" else "${mCustomVolume}%"
        )
        var selectedIndex by remember { mutableStateOf<Int?>(null) }
        CustomTip(
            text = c.getString(R.string.temp4)
        )
        if (mCustomVolume != -1) {
            CustomTip(
                text = c.getString(R.string.tip_edit_custom_volume_button)
            )
        }
        Text(
            text = c.getString(R.string.media_volume)
        )
        CustomSpacerPadding()
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth(),
            space = SegmentedButtonDefaults.BorderWidth
        ) {
            var showDialog by remember { mutableStateOf(false) }
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    onClick = {
                        selectedIndex = index
                        when (index) {
                            0 -> {
                                AudioUtils(c).setVolume(0)
                            }

                            1 -> {
                                AudioUtils(c).setVolume((0.4 * maxVolume).toInt())
                            }

                            2 -> {
                                AudioUtils(c).setVolume((0.6 * maxVolume).toInt())
                            }

                            3 -> {
                                if (mCustomVolume != -1) {
                                    AudioUtils(c).setVolume((mCustomVolume * 0.01 * maxVolume).toInt())
                                } else
                                    showDialog = true
                            }
                        }
                    },
                    selected = index == selectedIndex,
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    )
                ) {
                    Text(
                        text = label
                    )
                }
            }
            if (showDialog) {
                var newCustomVolume by remember { mutableFloatStateOf(0f) }
                AlertDialog(
                    onDismissRequest = {
                        // Nothing
                    },
                    title = {
                        Text(text = "${c.getString(R.string.add_custom_volume)}\n${newCustomVolume.toInt()}")
                    },
                    text = {
                        Slider(
                            value = newCustomVolume,
                            onValueChange = {
                                newCustomVolume = it
                            },
                            valueRange = 0f..100f,
                            steps = 19
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                SettingsViewModel().setCustomVolume(c, newCustomVolume.toInt())
                                mCustomVolume = newCustomVolume.toInt()
                                showDialog = false
                            }
                        ) {
                            Text(
                                text = c.getString(android.R.string.ok)
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text(
                                text = c.getString(android.R.string.cancel)
                            )
                        }
                    },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_dialog))
                )
            }
        }
    }
}