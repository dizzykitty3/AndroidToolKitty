package me.dizzykitty3.androidtoolkitty.ui.view

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.ui.view.edit_home_screen.EditHomeScreen
import me.dizzykitty3.androidtoolkitty.ui.view.home_screen.HomeScreen
import me.dizzykitty3.androidtoolkitty.ui.view.permission_request_screen.PermissionRequestScreen
import me.dizzykitty3.androidtoolkitty.ui.view.qr_code_generator_screen.QRCodeGeneratorScreen
import me.dizzykitty3.androidtoolkitty.ui.view.settings_screen.SettingsScreen
import me.dizzykitty3.androidtoolkitty.utils.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.utils.HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.utils.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.utils.QR_CODE_GENERATOR_SCREEN
import me.dizzykitty3.androidtoolkitty.utils.SETTINGS_SCREEN

@Composable
fun AppNavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN,
        enterTransition = { fadeIn(animationSpec = tween(durationMillis = 100)) },
        exitTransition = { fadeOut(animationSpec = tween(durationMillis = 100)) }
    ) {
        composable(HOME_SCREEN) { HomeScreen(navController) }
        composable(SETTINGS_SCREEN) { SettingsScreen(navController) }
        composable(EDIT_HOME_SCREEN) { EditHomeScreen() }
        composable(PERMISSION_REQUEST_SCREEN) { PermissionRequestScreen() }
        composable(QR_CODE_GENERATOR_SCREEN) { QRCodeGeneratorScreen() }
    }
}