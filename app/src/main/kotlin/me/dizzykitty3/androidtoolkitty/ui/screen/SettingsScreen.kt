package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomScreen
import me.dizzykitty3.androidtoolkitty.ui.card.AboutCard
import me.dizzykitty3.androidtoolkitty.ui.card.SettingsCard

@Composable
fun SettingsScreen(navController: NavHostController) {
    CustomScreen {
        SettingsCard(navController)
        AboutCard()
    }
}