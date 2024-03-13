package me.dizzykitty3.androidtoolkitty.common.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun CustomGradientText(
    textToDisplay: String,
    colors: List<Color>
) {
    Row {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            val text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        brush = Brush.horizontalGradient(
                            colors = colors
                        )
                    )
                ) {
                    append(textToDisplay)
                }
            }
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

@Composable
fun AnnotatedString.Builder.CustomItalicText(
    text: String
) {
    val italicTextStyle = SpanStyle(
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Light
    )
    withStyle(italicTextStyle) {
        append(text)
    }
}