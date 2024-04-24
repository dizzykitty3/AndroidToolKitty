package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomScreen
import me.dizzykitty3.androidtoolkitty.ui.card.PermissionRequestCard

@Composable
fun PermissionRequestScreen() {
    CustomScreen {
        PermissionRequestCard()
    }
}