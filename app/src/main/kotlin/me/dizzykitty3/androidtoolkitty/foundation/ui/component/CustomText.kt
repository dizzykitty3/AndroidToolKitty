package me.dizzykitty3.androidtoolkitty.foundation.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun CustomGradientText(
    textToDisplay: String,
    colors: List<Color>
) {
    Row {
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

@Composable
fun AnnotatedString.Builder.CustomBoldText(
    resId: Int
) {
    val boldTextStyle = SpanStyle(
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold
    )
    withStyle(boldTextStyle) {
        append(stringResource(id = resId))
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

@Composable
fun CustomTip(
    resId: Int
) {
    CustomTip(formattedMessage = stringResource(id = resId))
}

@Composable
fun CustomTip(
    formattedMessage: String
) {
    Card(
        modifier = Modifier.padding(bottom = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Outlined.Info,
                contentDescription = stringResource(id = R.string.info),
                modifier = Modifier.size(24.dp)
            )
            CustomIconAndTextPadding()
            Text(
                text = formattedMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun CustomGroupTitleText(
    resId: Int
) {
    Text(
        text = stringResource(id = resId),
        style = MaterialTheme.typography.titleMedium
    )
    CustomSpacerPadding()
}