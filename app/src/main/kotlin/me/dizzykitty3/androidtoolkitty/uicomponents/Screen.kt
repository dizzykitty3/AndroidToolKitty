package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun Screen(content: @Composable () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val screenPadding = dimensionResource(R.dimen.padding_screen)

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(start = screenPadding, end = screenPadding, bottom = screenPadding)
        ) {
            item { content() }
        }
    }
}