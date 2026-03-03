package me.dizzykitty3.androidtoolkitty

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.home.CustomVolumeScreen
import me.dizzykitty3.androidtoolkitty.home.Home
import me.dizzykitty3.androidtoolkitty.home.PermissionRequest
import me.dizzykitty3.androidtoolkitty.home.SearchScreen
import me.dizzykitty3.androidtoolkitty.home.SysSettingsScreen
import me.dizzykitty3.androidtoolkitty.home.VolumeScreen
import me.dizzykitty3.androidtoolkitty.home.WheelOfFortuneScreen
import me.dizzykitty3.androidtoolkitty.settings.Settings
import me.dizzykitty3.androidtoolkitty.settings.SysSettingsCardEditScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier, settingsViewModel: SettingsViewModel
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SCR_HOME,
    ) {
        composable(SCR_HOME) { Home(settingsViewModel, navController) }
        composable(SCR_SETTINGS) { Settings(settingsViewModel, navController) }
        composable(SCR_PERMISSION_REQUEST) { PermissionRequest(navController) }
        composable(SCR_SEARCH) { SearchScreen(settingsViewModel, navController) }
        composable(SCR_SYS_SETTINGS) { SysSettingsScreen(navController) }
        composable(SCR_VOLUME) { VolumeScreen(navController) }
        composable(SCR_WHEEL_OF_FORTUNE) { WheelOfFortuneScreen(navController) }
        composable(SCR_PIN_OPTIONS) { SysSettingsCardEditScreen(navController) }
        composable(SCR_CUSTOM_VOLUME) { CustomVolumeScreen(navController) }
    }
}
