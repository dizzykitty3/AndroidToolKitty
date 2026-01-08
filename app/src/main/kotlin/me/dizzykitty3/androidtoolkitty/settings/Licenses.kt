package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.util.withContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.uicomponents.TopBar

@Composable
fun Licenses(navController: NavHostController) {
    Box(Modifier.fillMaxSize()) {
        Column {
            TopBar(
                screenTitle = stringResource(R.string.licenses),
                navController = navController,
            )
            Surface(shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_shape))) {
                LibrariesContainer(
                    libraries = Libs.Builder().withContext(appContext).build(),
                )
            }
        }
    }
}