package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomGradientText
import me.dizzykitty3.androidtoolkitty.foundation.utils.TString

@Composable
fun Greeting() {
    CustomGradientText(
        textToDisplay = TString.greeting(),
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.tertiaryContainer
        )
    )
}