package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.dimensionResource
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
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref

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
    @StringRes id: Int
) {
    val boldTextStyle = SpanStyle(
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold
    )
    withStyle(boldTextStyle) {
        append(stringResource(id = id))
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
    @StringRes id: Int
) {
    CustomTip(formattedMessage = stringResource(id = id))
}

@Composable
fun CustomTip(
    formattedMessage: String
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_tip)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_tip))
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
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
    CustomCardSpacePadding()
}

@Composable
fun CustomGroupTitleText(
    @StringRes id: Int
) {
    val showDivider = SettingsSharedPref.showDivider

    if (showDivider) {
        Text(
            text = stringResource(id = id),
            style = MaterialTheme.typography.titleMedium
        )
    } else {
        Text(
            text = stringResource(id = id),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
    CustomSpacerPadding()
}