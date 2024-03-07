package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.ui.component.CustomGradientText
import me.dizzykitty3.androidtoolkitty.util.TextUtils.greeting

@Composable
fun GreetingText() {
    CustomGradientText(greeting())
}