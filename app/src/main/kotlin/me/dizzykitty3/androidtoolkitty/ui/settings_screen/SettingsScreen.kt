package me.dizzykitty3.androidtoolkitty.ui.settings_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.ui_components.CustomScreen

@Composable
fun SettingsScreen(navController: NavHostController) {
    CustomScreen {
        Settings(navController)
        About(navController)
    }
}