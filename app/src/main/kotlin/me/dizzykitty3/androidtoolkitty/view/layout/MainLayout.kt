package me.dizzykitty3.androidtoolkitty.view.layout

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainLayout() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") {
            HomeScreen(navController)
        }
        composable("SettingsScreen") {
            SettingsScreen(navController)
        }
    }
}