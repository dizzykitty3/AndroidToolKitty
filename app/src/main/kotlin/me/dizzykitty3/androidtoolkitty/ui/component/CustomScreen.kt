package me.dizzykitty3.androidtoolkitty.ui.component

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
        modifier = Modifier.padding(
            start = screenPadding,
            end = screenPadding
        )
    ) {
        item { TopPadding() }
        item { content() }
        item { BottomPadding() }
    }
}