package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun CustomItalicText(text: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Light
                )
            ) {
                append(text)
            }
        }
    )
}