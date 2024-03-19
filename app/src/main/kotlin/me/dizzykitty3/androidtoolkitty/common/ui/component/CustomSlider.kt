package me.dizzykitty3.androidtoolkitty.common.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.common.util.AudioUtils

@Composable
fun CustomVolumeSlider() {
    val c = LocalContext.current
    Column {
        val mVolume = AudioUtils(c).getVolume()
        var volume by remember { mutableIntStateOf(mVolume) }
        val maxVolume = AudioUtils(c).getMaxVolumeIndex()
        val volumeRange = 0f..maxVolume.toFloat()
        Slider(
            value = volume.toFloat(),
            onValueChange = { newVolume ->
                volume = newVolume.toInt()
                AudioUtils(c).setVolume(newVolume.toInt())
            },
            valueRange = volumeRange,
            steps = 2,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        )
    }
}