package me.dizzykitty3.androidtoolkitty.ui

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
import me.dizzykitty3.androidtoolkitty.data.SCR_DEBUGGING
import me.dizzykitty3.androidtoolkitty.data.SCR_EDIT_HOME
import me.dizzykitty3.androidtoolkitty.data.SCR_HOME
import me.dizzykitty3.androidtoolkitty.data.SCR_LICENSES
import me.dizzykitty3.androidtoolkitty.data.SCR_PERMISSION_REQUEST
import me.dizzykitty3.androidtoolkitty.data.SCR_QR_CODE_GENERATOR
import me.dizzykitty3.androidtoolkitty.data.SCR_SETTINGS
import me.dizzykitty3.androidtoolkitty.ui.screens.PermissionRequest
import me.dizzykitty3.androidtoolkitty.ui.screens.QRCodeGenerator
import me.dizzykitty3.androidtoolkitty.ui.screens.home.Home
import me.dizzykitty3.androidtoolkitty.ui.screens.settings.DebuggingScreen
import me.dizzykitty3.androidtoolkitty.ui.screens.settings.HomeEdit
import me.dizzykitty3.androidtoolkitty.ui.screens.settings.Licenses
import me.dizzykitty3.androidtoolkitty.ui.screens.settings.Settings
import me.dizzykitty3.androidtoolkitty.ui.viewmodel.SettingsViewModel

@Composable
fun AppNavHost(modifier: Modifier, settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()
    val fadeAnimation = settingsViewModel.settings.value.fadeAnimation

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SCR_HOME,
        enterTransition = {
            if (fadeAnimation)
                fadeIn(animationSpec = tween(FADE_DURATION)) +
                        scaleIn(
                            initialScale = INITIAL_SCALE,
                            animationSpec = tween(SCALE_DURATION)
                        )
            else slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(SLIDE_DURATION)
            )
        },
        popExitTransition = {
            if (fadeAnimation)
                fadeOut(animationSpec = tween(FADE_DURATION)) +
                        scaleOut(
                            targetScale = INITIAL_SCALE,
                            animationSpec = tween(SCALE_DURATION)
                        )
            else slideOutHorizontally(
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
        composable(SCR_HOME) { Home(settingsViewModel, navController) }
        composable(SCR_SETTINGS) { Settings(settingsViewModel, navController) }
        composable(SCR_EDIT_HOME) { HomeEdit(settingsViewModel) }
        composable(SCR_PERMISSION_REQUEST) { PermissionRequest(settingsViewModel) }
        composable(SCR_QR_CODE_GENERATOR) { QRCodeGenerator(settingsViewModel) }
        composable(SCR_DEBUGGING) { DebuggingScreen(settingsViewModel, navController) }
        composable(SCR_LICENSES) { Licenses() }
    }
}

private const val FADE_DURATION = 300
private const val SCALE_DURATION = 400
private const val SLIDE_DURATION = 400
private const val INITIAL_SCALE = 0.9f