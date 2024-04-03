package me.dizzykitty3.androidtoolkitty.foundation.ui_component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
private fun CustomPadding(dimen: Int) {
    Spacer(modifier = Modifier.padding(dimensionResource(id = dimen)))
}

@Composable
fun CustomCardSpacePadding() {
    CustomPadding(dimen = R.dimen.padding_card_space)
}

@Composable
fun CustomSpacerPadding() {
    CustomPadding(dimen = R.dimen.padding_spacer)
}

@Composable
fun CustomIconAndTextPadding() {
    CustomPadding(dimen = R.dimen.padding_icons_and_text)
}

@Composable
fun CustomOneHandedModePadding() {
    CustomPadding(dimen = R.dimen.padding_one_handed_mode)
}

@Composable
fun CustomTopPadding() {
    CustomPadding(dimen = R.dimen.padding_top)
}

@Composable
fun CustomBottomPadding() {
    CustomPadding(dimen = R.dimen.padding_bottom)
}