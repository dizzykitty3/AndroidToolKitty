@file:Suppress("unused")

package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.ErrorOutline
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
fun Tip(@StringRes message: Int, type: Int = 1) = Tip(stringResource(message), type)

@Composable
fun Tip(msg: String, type: Int = 1) {
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.padding_tip)),
        colors = CardDefaults.cardColors(
            containerColor = if (type == 1) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.secondaryContainer,
            contentColor = if (type == 1) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Row(
            Modifier
                .padding(dimensionResource(R.dimen.padding_tip))
                .fillMaxWidth()
        ) {
            Icon(
                if (type == 1) Icons.Outlined.Info else Icons.Outlined.BugReport,
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
fun WIPTip() = Tip(R.string.wip_long)

@Composable
fun DevBuildTip() = Tip(R.string.debug_build_top_tip, type = 2)

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
fun ScrollableItalicText(@StringRes text: Int) =
    Box(Modifier.horizontalScroll(rememberScrollState())) {
        Text(buildAnnotatedString { ItalicText(stringResource(text)) })
    }

@Composable
fun ScrollableItalicText(text: String) =
    Box(Modifier.horizontalScroll(rememberScrollState())) {
        Text(buildAnnotatedString { ItalicText(text) })
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
        Column(Modifier.weight(0.3F)) { LabelText(label) }
        Column(Modifier.weight(0.7F)) { ScrollableText(text) }
    }

@Composable
fun LabelAndValueTextRow(@StringRes label: Int, text: String) =
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(0.3F)) { LabelText(stringResource(label)) }
        Column(Modifier.weight(0.7F)) { ScrollableText(text) }
    }
