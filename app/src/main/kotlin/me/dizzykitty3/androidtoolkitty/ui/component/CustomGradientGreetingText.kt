package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import me.dizzykitty3.androidtoolkitty.util.Utils.greeting

@Composable
fun CustomGradientGreetingText() {
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF9796F0),
                        Color(0xFFFBC7D4)
                    )
                )
            )
        ) {
            append(greeting())
        }
    }
    Row {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                style = TextStyle.Default.copy(
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Black,
                    fontSize = 40.sp,
                    lineHeight = 40.0.sp,
                    letterSpacing = 0.0.sp
                )
            )
        }
    }
}