package me.dizzykitty3.androidtoolkitty.ui.permission_request_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.ui_components.CustomScreen

@Preview(showSystemUi = true)
@Composable
fun PermissionRequestScreen() {
    CustomScreen {
        PermissionRequestCard()
    }
}