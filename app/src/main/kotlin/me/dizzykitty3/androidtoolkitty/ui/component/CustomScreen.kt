package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun CustomScreen(content: @Composable () -> Unit) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card_content)

    LazyColumn(
        modifier = Modifier.padding(
            start = cardPadding,
            end = cardPadding
        )
    ) {
        item { TopPadding() }
        item { content() }
        item { BottomPadding() }
    }
}