package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.uicomponents.Gradient
import me.dizzykitty3.androidtoolkitty.utils.DateUtil

@Composable
fun Greeting() {
    Gradient(
        textToDisplay = DateUtil.greeting(),
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.tertiaryContainer
        )
    )
}