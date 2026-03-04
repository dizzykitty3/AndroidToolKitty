package me.dizzykitty3.androidtoolkitty

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.home.CustomVolumeScreen
import me.dizzykitty3.androidtoolkitty.home.Home
import me.dizzykitty3.androidtoolkitty.home.VolumeScreen
import me.dizzykitty3.androidtoolkitty.settings.Settings

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SCR_HOME,
    ) {
        composable(SCR_HOME) { Home(navController) }
        composable(SCR_SETTINGS) { Settings(navController) }
        composable(SCR_VOLUME) { VolumeScreen(navController) }
        composable(SCR_CUSTOM_VOLUME) { CustomVolumeScreen(navController) }
    }
}
