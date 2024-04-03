package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomGradientText
import me.dizzykitty3.androidtoolkitty.foundation.utils.TString

@Composable
fun GreetingText() {
    CustomGradientText(
        textToDisplay = TString.greeting(),
        colors = listOf(
            Color(0xFF9796F0),
            Color(0xFFFBC7D4)
        )
    )
}