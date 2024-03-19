package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.util.AudioUtils

@Composable
fun VolumeCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.AutoMirrored.Outlined.VolumeUp,
        title = c.getString(R.string.volume),
        id = "card_volume"
    ) {
        val maxVolume = AudioUtils(c).getMaxVolumeIndex()
        val mVolume = AudioUtils(c).getVolume()
        var volume by remember { mutableIntStateOf(mVolume) }
        val volumeRange = 0f..maxVolume.toFloat()
        Text(
            text = c.getString(R.string.media_volume)
        )
        Slider(
            value = volume.toFloat(),
            onValueChange = { newVolume ->
                volume = newVolume.toInt()
                AudioUtils(c).setVolume(newVolume.toInt())
            },
            valueRange = volumeRange,
            steps = 4, // 4 steps = 5 options
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    AudioUtils(c).setVolume(0)
                }
            ) {
                Text(
                    text = "0%"
                )
            }
            Button(
                onClick = {
                    AudioUtils(c).setVolume((0.2 * maxVolume).toInt())
                }
            ) {
                Text(
                    text = "20%"
                )
            }
            Button(
                onClick = {
                    AudioUtils(c).setVolume((0.4 * maxVolume).toInt())
                }
            ) {
                Text(
                    text = "40%"
                )
            }
        }

    }
}