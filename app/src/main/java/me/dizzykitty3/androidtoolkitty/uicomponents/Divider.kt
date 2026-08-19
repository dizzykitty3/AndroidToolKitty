package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RowDivider() {
    SpacerPadding()
    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1F))
    SpacerPadding()
}

@Composable
fun ButtonDivider() {
    Text(
        "|",
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
    )
}