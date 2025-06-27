package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mikepenz.aboutlibraries.ui.compose.android.rememberLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import me.dizzykitty3.androidtoolkitty.uicomponents.NavBackButton

@Composable
fun Licenses(navController: NavHostController) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            NavBackButton(navController)
            LibrariesContainer(libraries = rememberLibraries().value)
        }
    }
}