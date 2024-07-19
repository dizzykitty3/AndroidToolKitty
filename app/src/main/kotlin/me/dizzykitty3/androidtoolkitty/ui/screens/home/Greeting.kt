package me.dizzykitty3.androidtoolkitty.ui.screens.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.domain.utils.DateUtil
import me.dizzykitty3.androidtoolkitty.ui.components.Gradient

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