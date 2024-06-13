package me.dizzykitty3.androidtoolkitty.ui_components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import me.dizzykitty3.androidtoolkitty.utils.DateUtil

@Composable
fun CustomAnimatedProgressIndicator() {
    val progress = remember { Animatable(0f) }
    val targetProgress = DateUtil.yearProgress
    val durationMillis = 2000

    LaunchedEffect(true) {
        progress.animateTo(
            targetProgress,
            animationSpec = tween(durationMillis)
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        SpacerPadding()
        LinearProgressIndicator(
            progress = { progress.value },
            trackColor = MaterialTheme.colorScheme.surfaceContainer,
            modifier = Modifier.fillMaxWidth()
        )
        SpacerPadding()
    }
}