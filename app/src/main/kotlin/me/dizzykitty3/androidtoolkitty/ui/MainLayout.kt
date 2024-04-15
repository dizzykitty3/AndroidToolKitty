package me.dizzykitty3.androidtoolkitty.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.ui.screen.BluetoothDevicesScreen
import me.dizzykitty3.androidtoolkitty.ui.screen.EditHomePageScreen
import me.dizzykitty3.androidtoolkitty.ui.screen.HomeScreen
import me.dizzykitty3.androidtoolkitty.ui.screen.LuckySpinningWheelScreen
import me.dizzykitty3.androidtoolkitty.ui.screen.PermissionRequestScreen
import me.dizzykitty3.androidtoolkitty.ui.screen.SettingsScreen

private const val HOME_SCREEN = "HomeScreen"
private const val SETTINGS_SCREEN = "SettingsScreen"
private const val EDIT_HOME_PAGE_SCREEN = "EditHomePageScreen"
private const val LUCKY_SPINNING_WHEEL_SCREEN = "LuckySpinningWheelScreen"
private const val BLUETOOTH_DEVICES_SCREEN = "BluetoothDevicesScreen"
private const val PERMISSION_REQUEST_SCREEN = "PermissionRequestScreen"

@Composable
fun MainLayout() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN,
        enterTransition = { fadeIn(animationSpec = tween(durationMillis = 100)) },
        exitTransition = { fadeOut(animationSpec = tween(durationMillis = 100)) }
    ) {
        composable(HOME_SCREEN) { HomeScreen(navController) }
        composable(SETTINGS_SCREEN) { SettingsScreen(navController) }
        composable(EDIT_HOME_PAGE_SCREEN) { EditHomePageScreen() }
        composable(LUCKY_SPINNING_WHEEL_SCREEN) { LuckySpinningWheelScreen() }
        composable(BLUETOOTH_DEVICES_SCREEN) { BluetoothDevicesScreen(navController) }
        composable(PERMISSION_REQUEST_SCREEN) { PermissionRequestScreen() }
    }
}