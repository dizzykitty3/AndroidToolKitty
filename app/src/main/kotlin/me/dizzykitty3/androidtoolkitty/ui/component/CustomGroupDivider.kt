package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref

@Composable
fun CustomGroupDivider() {
    val showDivider = SettingsSharedPref.showDivider

    if (showDivider) {
        CustomSpacerPadding()
        CustomSpacerPadding()
        HorizontalDivider()
        CustomSpacerPadding()
        CustomSpacerPadding()
    } else {
        CustomSpacerPadding()
    }
}