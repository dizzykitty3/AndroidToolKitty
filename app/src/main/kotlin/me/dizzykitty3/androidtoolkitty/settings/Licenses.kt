package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.android.rememberLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

@Composable
fun Licenses() {
    Box(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        LibrariesContainer(libraries = rememberLibraries().value)
    }
}