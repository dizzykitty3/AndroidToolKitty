package me.dizzykitty3.androidtoolkitty

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.home.AndroidVersionHistoryScreen
import me.dizzykitty3.androidtoolkitty.home.CodesOfCharactersScreen
import me.dizzykitty3.androidtoolkitty.home.ComposeCatalogScreen
import me.dizzykitty3.androidtoolkitty.home.FontWeightTestScreen
import me.dizzykitty3.androidtoolkitty.home.Home
import me.dizzykitty3.androidtoolkitty.home.PermissionRequest
import me.dizzykitty3.androidtoolkitty.home.SearchScreen
import me.dizzykitty3.androidtoolkitty.home.SysSettingsScreen
import me.dizzykitty3.androidtoolkitty.home.VolumeScreen
import me.dizzykitty3.androidtoolkitty.settings.HomeEdit
import me.dizzykitty3.androidtoolkitty.settings.Licenses
import me.dizzykitty3.androidtoolkitty.settings.Settings

@Composable
fun AppNavHost(
    modifier: Modifier,
    settingsViewModel: SettingsViewModel,
    widthType: Int,
    customAnimation: Boolean = false
) {
    val navController = rememberNavController()

    if (customAnimation) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = SCR_HOME,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(SLIDE_DURATION)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(SLIDE_DURATION)
                )
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(FADE_DURATION)) +
                        scaleIn(
                            initialScale = INITIAL_SCALE,
                            animationSpec = tween(SCALE_DURATION)
                        )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(FADE_DURATION)) +
                        scaleOut(
                            targetScale = INITIAL_SCALE,
                            animationSpec = tween(SCALE_DURATION)
                        )
            }) {
            composable(SCR_HOME) { Home(settingsViewModel, navController, widthType) }
            composable(SCR_SETTINGS) { Settings(settingsViewModel, navController) }
            composable(SCR_EDIT_HOME) { HomeEdit() }
            composable(SCR_PERMISSION_REQUEST) { PermissionRequest() }
            composable(SCR_LICENSES) { Licenses() }
            composable(SCR_SEARCH) { SearchScreen(settingsViewModel) }
            composable(SCR_CODES_OF_CHARACTERS) { CodesOfCharactersScreen() }
            composable(SCR_ANDROID_VERSION_HISTORY) { AndroidVersionHistoryScreen() }
            composable(SCR_FONT_WEIGHT_TEST) { FontWeightTestScreen() }
            composable(SCR_SYS_SETTINGS) { SysSettingsScreen() }
            composable(SCR_VOLUME) { VolumeScreen() }
            composable(SCR_COMPOSE_CATALOG) { ComposeCatalogScreen() }
        }
    } else {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = SCR_HOME
        ) {
            composable(SCR_HOME) { Home(settingsViewModel, navController, widthType) }
            composable(SCR_SETTINGS) { Settings(settingsViewModel, navController) }
            composable(SCR_EDIT_HOME) { HomeEdit() }
            composable(SCR_PERMISSION_REQUEST) { PermissionRequest() }
            composable(SCR_LICENSES) { Licenses() }
            composable(SCR_SEARCH) { SearchScreen(settingsViewModel) }
            composable(SCR_CODES_OF_CHARACTERS) { CodesOfCharactersScreen() }
            composable(SCR_ANDROID_VERSION_HISTORY) { AndroidVersionHistoryScreen() }
            composable(SCR_FONT_WEIGHT_TEST) { FontWeightTestScreen() }
            composable(SCR_SYS_SETTINGS) { SysSettingsScreen() }
            composable(SCR_VOLUME) { VolumeScreen() }
            composable(SCR_COMPOSE_CATALOG) { ComposeCatalogScreen() }
        }
    }
}

private const val FADE_DURATION = 300
private const val SCALE_DURATION = 400
private const val SLIDE_DURATION = 400
private const val INITIAL_SCALE = 0.9f