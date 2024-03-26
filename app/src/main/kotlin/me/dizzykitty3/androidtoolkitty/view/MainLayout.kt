package me.dizzykitty3.androidtoolkitty.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.view.layout.HideCardSettingScreen
import me.dizzykitty3.androidtoolkitty.view.layout.HomeScreen
import me.dizzykitty3.androidtoolkitty.view.layout.LuckySpinningWheelScreen
import me.dizzykitty3.androidtoolkitty.view.layout.SettingsScreen

@Composable
fun MainLayout() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") { HomeScreen(navController) }
        composable("SettingsScreen") { SettingsScreen(navController) }
        composable("HideCardSettingScreen") { HideCardSettingScreen() }
        composable("LuckySpinningWheelScreen") { LuckySpinningWheelScreen() }
    }
}