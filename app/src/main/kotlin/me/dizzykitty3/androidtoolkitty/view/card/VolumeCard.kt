package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.common.util.AudioUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.AutoMirrored.Outlined.VolumeUp,
        title = c.getString(R.string.volume),
        id = "card_volume"
    ) {
        val maxVolume = AudioUtils(c).getMaxVolumeIndex()
        val options = listOf("0", "40%", "60%")
        var selectedIndex by remember { mutableStateOf<Int?>(null) }
        CustomTip(
            text = c.getString(R.string.temp4)
        )
        Text(
            text = c.getString(R.string.media_volume)
        )
        CustomSpacerPadding()
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
                                AudioUtils(c).setVolume(0)
                            }

                            1 -> {
                                AudioUtils(c).setVolume((0.4 * maxVolume).toInt())
                            }

                            2 -> {
                                AudioUtils(c).setVolume((0.6 * maxVolume).toInt())
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
        }
    }
}