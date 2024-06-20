package me.dizzykitty3.androidtoolkitty.ui.settings

import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui_components.Card
import me.dizzykitty3.androidtoolkitty.ui_components.Screen
import me.dizzykitty3.androidtoolkitty.ui_components.UnderDevelopmentTip

@Composable
fun Licenses() {
    Screen { Card(title = R.string.licenses) { UnderDevelopmentTip() } }
}