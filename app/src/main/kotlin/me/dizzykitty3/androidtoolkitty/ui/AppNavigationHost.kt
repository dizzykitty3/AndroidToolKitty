package me.dizzykitty3.androidtoolkitty.ui

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.QR_CODE_GENERATOR_SCREEN
import me.dizzykitty3.androidtoolkitty.SETTINGS_SCREEN
import me.dizzykitty3.androidtoolkitty.ui.edit.HomeEdit
import me.dizzykitty3.androidtoolkitty.ui.home.Home
import me.dizzykitty3.androidtoolkitty.ui.permission_request.PermissionRequest
import me.dizzykitty3.androidtoolkitty.ui.qr_code_generator.QRCodeGenerator
import me.dizzykitty3.androidtoolkitty.ui.settings.Settings

@Composable
fun AppNavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN,
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(300, easing = EaseIn), initialOffsetX = { it / 2 },
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                animationSpec = tween(300, easing = EaseOut), targetOffsetX = { it },
            )
        },
        popEnterTransition = { fadeIn(animationSpec = tween(300, easing = EaseIn)) },
        exitTransition = { fadeOut(animationSpec = tween(300, easing = EaseOut)) }) {
        composable(HOME_SCREEN) { Home(navController) }
        composable(SETTINGS_SCREEN) { Settings(navController) }
        composable(EDIT_HOME_SCREEN) { HomeEdit() }
        composable(PERMISSION_REQUEST_SCREEN) { PermissionRequest() }
        composable(QR_CODE_GENERATOR_SCREEN) { QRCodeGenerator() }
    }
}