package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun CustomCardSpacePadding() {
    val cardSpacePadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card_space))
    Spacer(
        modifier = cardSpacePadding
    )
}