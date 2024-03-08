package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomGradientText
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding

@Composable
fun TestCard() {
    val text = "Android ToolKitty"
    CustomCard(
        title = "test", isExpand = false
    ) {
        var clicks by remember { mutableIntStateOf(0) }
        ClickCounter(
            clicks = clicks,
            onClick = {
                clicks++
            }
        )
        CustomSpacerPadding()
        CustomGradientText(
            textToDisplay = text,
            colors = listOf(
                Color(0xFF9796F0),
                Color(0xFF9796F0)
            )
        )
        CustomGradientText(
            textToDisplay = text,
            colors = listOf(
                Color(0xFFC9D6FF),
                Color(0xFFE2E2E2)
            )
        )
        CustomGradientText(
            textToDisplay = text,
            colors = listOf(
                Color(0xFF20002C),
                Color(0xFFCBB4D4)
            )
        )
        CustomGradientText(
            textToDisplay = text,
            colors = listOf(
                Color(0xFF4568DC),
                Color(0xFFB06AB3)
            )
        )
    }
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(
            text = "I've been clicked $clicks times"
        )
    }
}