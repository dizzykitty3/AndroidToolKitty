package me.dizzykitty3.androidtoolkitty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.home.Home

@Composable
fun AppNavHost(modifier: Modifier) {
    NavHost(
        modifier = modifier,
        navController = rememberNavController(),
        startDestination = SCR_HOME,
    ) {
        composable(SCR_HOME) { Home() }
    }
}
