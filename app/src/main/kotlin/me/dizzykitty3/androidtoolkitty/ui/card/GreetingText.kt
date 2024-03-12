package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import me.dizzykitty3.androidtoolkitty.ui.component.CustomGradientText
import me.dizzykitty3.androidtoolkitty.util.StringUtils

@Composable
fun GreetingText() {
    CustomGradientText(
        textToDisplay = StringUtils.greeting(),
        colors = listOf(
            Color(0xFF9796F0),
            Color(0xFFFBC7D4)
        )
    )
}