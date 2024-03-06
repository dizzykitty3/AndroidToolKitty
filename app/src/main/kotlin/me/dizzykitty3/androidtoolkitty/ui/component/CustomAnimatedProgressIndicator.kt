package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import me.dizzykitty3.androidtoolkitty.Utils

@Composable
fun CustomAnimatedProgressIndicator() {
    val progress = remember { Animatable(0f) }
    val targetProgress = Utils.calculateYearProgress()
    LaunchedEffect(true) {
        progress.animateTo(
            targetProgress,
            animationSpec = tween(1500)
        )
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LinearProgressIndicator(
            progress = { progress.value },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
