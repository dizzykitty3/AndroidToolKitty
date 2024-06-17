package me.dizzykitty3.androidtoolkitty.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.QR_CODE_GENERATOR_SCREEN
import me.dizzykitty3.androidtoolkitty.SETTINGS_SCREEN
import me.dizzykitty3.androidtoolkitty.ui.edit_home_screen.EditHomeScreen
import me.dizzykitty3.androidtoolkitty.ui.home_screen.HomeScreen
import me.dizzykitty3.androidtoolkitty.ui.permission_request_screen.PermissionRequestScreen
import me.dizzykitty3.androidtoolkitty.ui.qr_code_generator_screen.QRCodeGeneratorScreen
import me.dizzykitty3.androidtoolkitty.ui.settings_screen.SettingsScreen

@Composable
fun AppNavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ) {
        composable(HOME_SCREEN) { HomeScreen(navController) }
        composable(SETTINGS_SCREEN) { SettingsScreen(navController) }
        composable(EDIT_HOME_SCREEN) { EditHomeScreen() }
        composable(PERMISSION_REQUEST_SCREEN) { PermissionRequestScreen() }
        composable(QR_CODE_GENERATOR_SCREEN) { QRCodeGeneratorScreen() }
    }
}