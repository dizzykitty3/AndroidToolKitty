package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import me.dizzykitty3.androidtoolkitty.ui.component.CustomGradientText
import me.dizzykitty3.androidtoolkitty.util.TextUtils.greeting

@Composable
fun GreetingText() {
    CustomGradientText(
        textToDisplay = greeting(),
        colors = listOf(
            Color(0xFF9796F0),
            Color(0xFFFBC7D4)
        )
    )
}