package me.dizzykitty3.androidtoolkitty.view

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.view.layout.BluetoothDevicesScreen
import me.dizzykitty3.androidtoolkitty.view.layout.EditHomePageScreen
import me.dizzykitty3.androidtoolkitty.view.layout.HomeScreen
import me.dizzykitty3.androidtoolkitty.view.layout.LuckySpinningWheelScreen
import me.dizzykitty3.androidtoolkitty.view.layout.SettingsScreen

private const val HOME_SCREEN = "HomeScreen"
private const val SETTINGS_SCREEN = "SettingsScreen"
private const val EDIT_HOME_PAGE_SCREEN = "EditHomePageScreen"
private const val LUCKY_SPINNING_WHEEL_SCREEN = "LuckySpinningWheelScreen"
private const val BLUETOOTH_DEVICES_SCREEN = "BluetoothDevicesScreen"

@Composable
fun NavHostLayout() {
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
        composable(BLUETOOTH_DEVICES_SCREEN) { BluetoothDevicesScreen() }
    }
}