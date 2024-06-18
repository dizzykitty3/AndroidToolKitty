package me.dizzykitty3.androidtoolkitty.ui_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun CustomScreen(content: @Composable () -> Unit) {
    val screenPadding = dimensionResource(id = R.dimen.padding_screen)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = screenPadding,
                end = screenPadding
            )
    ) {
        item { TopPadding() }
        item { content() }
        item { BottomPadding() }
    }
}