package me.dizzykitty3.androidtoolkitty.ui.components

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
private fun Res(@DimenRes padding: Int) = Spacer(Modifier.padding(dimensionResource(padding)))

@Composable
fun CardSpacePadding() = Res(padding = R.dimen.padding_card_space)

@Composable
fun SpacerPadding() = Res(padding = R.dimen.padding_spacer)

/**
 * Note: Not for buttons.
 */
@Composable
fun IconAndTextPadding() = Res(padding = R.dimen.padding_icons_and_text)