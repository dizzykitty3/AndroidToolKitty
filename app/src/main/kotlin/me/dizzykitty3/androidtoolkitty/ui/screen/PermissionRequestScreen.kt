package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomScreen
import me.dizzykitty3.androidtoolkitty.ui.card.PermissionRequestCard

@Preview(showSystemUi = true)
@Composable
fun PermissionRequestScreen() {
    CustomScreen {
        PermissionRequestCard()
    }
}