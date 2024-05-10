package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGradientText
import me.dizzykitty3.androidtoolkitty.foundation.util.StringUtil

@Preview
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