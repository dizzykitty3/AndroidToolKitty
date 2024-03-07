package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding

@Composable
fun TestCard() {
    CustomCard(title = "test") {
        var clicks by remember { mutableIntStateOf(0) }
        ClickCounter(
            clicks = clicks,
            onClick = {
                clicks++
            }
        )
        CustomSpacerPadding()
        Text(
            text = "Default 中文测试 日本語テスト",
            fontFamily = FontFamily.Default
        )
        Text(
            text = "SansSerif 中文测试 日本語テスト",
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = "Serif 中文测试 日本語テスト",
            fontFamily = FontFamily.Serif
        )
        Text(
            text = "Monospace 中文测试 日本語テスト",
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = "Cursive 中文测试 日本語テスト",
            fontFamily = FontFamily.Cursive
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