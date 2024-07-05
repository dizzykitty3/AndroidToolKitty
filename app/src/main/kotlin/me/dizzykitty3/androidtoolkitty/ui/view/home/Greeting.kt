package me.dizzykitty3.androidtoolkitty.ui.view.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.data.utils.StringUtil
import me.dizzykitty3.androidtoolkitty.ui.components.Gradient

@Composable
@Preview
fun Greeting() {
    Gradient(
        textToDisplay = StringUtil.greeting(),
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.tertiaryContainer
        )
    )
}