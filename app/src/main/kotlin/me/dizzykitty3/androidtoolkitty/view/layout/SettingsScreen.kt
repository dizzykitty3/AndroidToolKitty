package me.dizzykitty3.androidtoolkitty.view.layout

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomScreen
import me.dizzykitty3.androidtoolkitty.view.card.AboutCard
import me.dizzykitty3.androidtoolkitty.view.card.SettingsCard

@Composable
fun SettingsScreen(navController: NavHostController) {
    CustomScreen {
        SettingsCard(navController)
        AboutCard()
    }
}