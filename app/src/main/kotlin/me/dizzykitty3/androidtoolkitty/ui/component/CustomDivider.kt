package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref

@Composable
fun GroupDivider() {
    val showDivider = SettingsSharedPref.showDivider

    SpacerPadding()
    if (showDivider) {
        SpacerPadding()
        HorizontalDivider()
        SpacerPadding()
    }
    SpacerPadding()
}