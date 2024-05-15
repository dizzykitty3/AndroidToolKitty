package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
private fun Res(@DimenRes paddingRes: Int) {
    Spacer(modifier = Modifier.padding(dimensionResource(id = paddingRes)))
}

@Composable
fun CardSpacePadding() {
    Res(paddingRes = R.dimen.padding_card_space)
}

@Composable
fun SpacerPadding() {
    Res(paddingRes = R.dimen.padding_spacer)
}

@Composable
fun IconAndTextPadding() {
    Res(paddingRes = R.dimen.padding_icons_and_text)
}

@Composable
fun OneHandedModePadding() {
    Res(paddingRes = R.dimen.padding_one_handed_mode)
}

@Composable
fun TopPadding() {
    Res(paddingRes = R.dimen.padding_top)
}

@Composable
fun BottomPadding() {
    Res(paddingRes = R.dimen.padding_bottom)
}