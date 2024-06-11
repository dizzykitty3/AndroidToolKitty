package me.dizzykitty3.androidtoolkitty.ui.view.settings_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.ui.component.CustomScreen

@Composable
fun SettingsScreen(navController: NavHostController) {
    CustomScreen {
        SettingsCard(navController)
        AboutCard(navController)
    }
}