package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGradientText
import me.dizzykitty3.androidtoolkitty.foundation.util.StringUtil

@Composable
fun Greeting() {
    CustomGradientText(
        textToDisplay = StringUtil.greeting(),
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.tertiaryContainer
        )
    )
}