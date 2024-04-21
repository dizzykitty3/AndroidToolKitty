package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomBottomPadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomTopPadding

/**
 * Example @Composable function of screens.
 */
@Suppress("unused")
@Composable
fun ExampleScreen(
    cardContent: @Composable () -> Unit
) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card_content)

    LazyColumn(
        modifier = Modifier.padding(
            start = cardPadding,
            end = cardPadding
        )
    ) {
        item { CustomTopPadding() }
        item { cardContent() }
        item { CustomBottomPadding() }
    }
}