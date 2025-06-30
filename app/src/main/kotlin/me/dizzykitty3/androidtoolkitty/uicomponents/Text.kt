@file:Suppress("unused")

package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.utils.StringUtil

@Composable
fun Gradient(
    textToDisplay: String,
    colors: List<Color>
) {
    Row {
        val text = buildAnnotatedString {
            withStyle(SpanStyle(Brush.horizontalGradient(colors))) {
                append(textToDisplay)
            }
        }
        Text(
            text,
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
fun GradientSmall(textToDisplay: String, colors: List<Color>) {
    Row {
        val text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    Brush.horizontalGradient(colors),
                    fontWeight = FontWeight.Black
                )
            ) { append(textToDisplay) }
        }
        Text(text)
    }
}

@Composable
fun AnnotatedString.Builder.ItalicText(@StringRes text: Int) {
    val italicTextStyle = SpanStyle(
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Light
    )
    withStyle(italicTextStyle) { append(stringResource(text)) }
}

@Composable
fun AnnotatedString.Builder.ItalicText(text: String) {
    val italicTextStyle = SpanStyle(
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Light
    )
    withStyle(italicTextStyle) { append(text) }
}

@Composable
fun AnnotatedString.Builder.PrimaryColorText(@StringRes id: Int) {
    val materialPrimaryColorStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
    withStyle(materialPrimaryColorStyle) { append(stringResource(id)) }
}

@Composable
fun Tip(@StringRes message: Int) = Tip(stringResource(message))

@Composable
fun Tip(msg: String) {
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.padding_tip)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Row(
            Modifier
                .padding(dimensionResource(R.dimen.padding_tip))
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Outlined.Info,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            IconAndTextPadding()
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
    SpacerPadding()
}

@Composable
fun ErrorTip(msg: String) {
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.padding_tip)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    ) {
        Row(
            Modifier
                .padding(dimensionResource(R.dimen.padding_tip))
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Outlined.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            IconAndTextPadding()
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
    SpacerPadding()
}

@Composable
fun DevBuildTip() = Tip(R.string.debug_build_top_tip)

@Composable
fun NoTranslationTip() = Tip(stringResource(R.string.no_translation, StringUtil.sysLocale))

@Composable
fun GroupTitle(@StringRes title: Int) = GroupTitle(stringResource(title))

@Composable
fun GroupTitle(title: String) {
    Text(
        title,
        color = MaterialTheme.colorScheme.primary
    )
    SpacerPadding()
}

@Composable
fun GroupTitleNoColor(title: String) = Text(title, style = MaterialTheme.typography.titleMedium)

@Composable
fun ScrollableText(text: String) =
    Box(Modifier.horizontalScroll(rememberScrollState())) { Text(text) }

@Composable
fun ScrollableText(@StringRes text: Int) = ScrollableText(stringResource(text))

@Composable
fun ScrollableBoldText(text: String) =
    Box(Modifier.horizontalScroll(rememberScrollState())) { Text(text, fontWeight = FontWeight.Bold) }

@Composable
fun ScrollableBoldText(@StringRes text: Int) = ScrollableBoldText(stringResource(text))

@Composable
fun ScrollableItalicText(@StringRes text: Int) =
    Box(Modifier.horizontalScroll(rememberScrollState())) {
        Text(buildAnnotatedString { ItalicText(stringResource(text)) }, maxLines = 1)
    }

@Composable
fun ScrollableItalicText(text: String) =
    Box(Modifier.horizontalScroll(rememberScrollState())) {
        Text(buildAnnotatedString { ItalicText(text) }, maxLines = 1)
    }

@Composable
fun Description(@StringRes text: Int) = Description(stringResource(text))

@Composable
fun Description(text: String) = Text(
    text,
    style = MaterialTheme.typography.labelMedium,
    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
)

@Composable
fun ScreenTitle(@StringRes text: Int) = ScreenTitle(stringResource(text))

@Composable
fun ScreenTitle(text: String) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text,
            style = MaterialTheme.typography.titleLarge
        )
    }
    SpacerPadding()
}

@Composable
fun LabelText(text: String) = Text(text, style = MaterialTheme.typography.labelMedium)

@Composable
fun LabelText(@StringRes text: Int) = LabelText(stringResource(text))

@Composable
fun LabelAndValueTextRow(label: String, text: String) =
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(0.4F)) { LabelText(label) }
        Column(Modifier.weight(0.6F)) { ScrollableText(text) }
    }

@Composable
fun LabelAndValueTextRow(@StringRes label: Int, text: String) =
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(0.4F)) { LabelText(stringResource(label)) }
        Column(Modifier.weight(0.6F)) { ScrollableText(text) }
    }
