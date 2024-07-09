package me.dizzykitty3.androidtoolkitty.ui

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.data.DEBUGGING_SCREEN
import me.dizzykitty3.androidtoolkitty.data.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.data.HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.data.LICENSES_SCREEN
import me.dizzykitty3.androidtoolkitty.data.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.data.QR_CODE_GENERATOR_SCREEN
import me.dizzykitty3.androidtoolkitty.data.SETTINGS_SCREEN
import me.dizzykitty3.androidtoolkitty.ui.screens.PermissionRequest
import me.dizzykitty3.androidtoolkitty.ui.screens.QRCodeGenerator
import me.dizzykitty3.androidtoolkitty.ui.screens.edit.HomeEdit
import me.dizzykitty3.androidtoolkitty.ui.screens.home.Home
import me.dizzykitty3.androidtoolkitty.ui.screens.settings.Debugging
import me.dizzykitty3.androidtoolkitty.ui.screens.settings.Licenses
import me.dizzykitty3.androidtoolkitty.ui.screens.settings.Settings
import me.dizzykitty3.androidtoolkitty.ui.screens.settings.model.SettingsViewModel

@Composable
fun AppNavHost(settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()
    val newAnimation = settingsViewModel.settings.value.enableNewAnimation

    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN,
        enterTransition = {
            if (newAnimation)
                fadeIn(animationSpec = tween(DEFAULT_FADE_DURATION)) +
                        scaleIn(
                            initialScale = DEFAULT_INITIAL_SCALE,
                            animationSpec = tween(DEFAULT_SCALE_DURATION)
                        )
            else slideInHorizontally(
                animationSpec = tween(300, easing = EaseIn), initialOffsetX = { it },
            )
        },
        popExitTransition = {
            if (newAnimation)
                fadeOut(animationSpec = tween(DEFAULT_FADE_DURATION)) +
                        scaleOut(
                            targetScale = DEFAULT_INITIAL_SCALE,
                            animationSpec = tween(DEFAULT_SCALE_DURATION)
                        )
            else slideOutHorizontally(
                animationSpec = tween(300, easing = EaseOut), targetOffsetX = { it },
            )
        },
        popEnterTransition = {
            if (newAnimation)
                fadeIn(animationSpec = tween(DEFAULT_FADE_DURATION)) +
                        scaleIn(
                            initialScale = DEFAULT_INITIAL_SCALE,
                            animationSpec = tween(DEFAULT_SCALE_DURATION)
                        )
            else fadeIn(animationSpec = tween(300, easing = EaseIn))
        },
        exitTransition = {
            if (newAnimation)
                fadeOut(animationSpec = tween(DEFAULT_FADE_DURATION)) +
                        scaleOut(
                            targetScale = DEFAULT_INITIAL_SCALE,
                            animationSpec = tween(DEFAULT_SCALE_DURATION)
                        )
            else fadeOut(animationSpec = tween(300, easing = EaseOut))
        }) {
        composable(HOME_SCREEN) { Home(settingsViewModel, navController) }
        composable(SETTINGS_SCREEN) { Settings(settingsViewModel, navController) }
        composable(EDIT_HOME_SCREEN) { HomeEdit(settingsViewModel) }
        composable(PERMISSION_REQUEST_SCREEN) { PermissionRequest(settingsViewModel) }
        composable(QR_CODE_GENERATOR_SCREEN) { QRCodeGenerator(settingsViewModel) }
        composable(DEBUGGING_SCREEN) { Debugging(settingsViewModel, navController) }
        composable(LICENSES_SCREEN) { Licenses() }
    }
}

private const val DEFAULT_FADE_DURATION = 300
private const val DEFAULT_SCALE_DURATION = 400
private const val DEFAULT_INITIAL_SCALE = 0.9f