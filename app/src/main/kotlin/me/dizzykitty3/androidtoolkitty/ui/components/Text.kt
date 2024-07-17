package me.dizzykitty3.androidtoolkitty.ui.components

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
import me.dizzykitty3.androidtoolkitty.ui.viewmodel.SettingsViewModel

@Composable
fun Gradient(
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
            ) { append(textToDisplay) }
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
fun GradientSmall(textToDisplay: String, colors: List<Color>) {
    Row {
        val text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    brush = Brush.horizontalGradient(colors = colors),
                    fontWeight = FontWeight.Black
                )
            ) { append(textToDisplay) }
        }
        Text(text)
    }
}

@Composable
fun AnnotatedString.Builder.Italic(text: String) {
    val italicTextStyle = SpanStyle(
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Light
    )
    withStyle(italicTextStyle) { append(text) }
}

@Composable
fun AnnotatedString.Builder.PrimaryColor(@StringRes id: Int) {
    val materialPrimaryColorStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
    withStyle(materialPrimaryColorStyle) { append(stringResource(id)) }
}

@Composable
fun Tip(settingsViewModel: SettingsViewModel, @StringRes message: Int) =
    Tip(settingsViewModel, stringResource(message))

@Composable
fun WIPTip() {
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
                contentDescription = stringResource(R.string.info),
                modifier = Modifier.size(24.dp)
            )
            IconAndTextPadding()
            Text(
                text = stringResource(R.string.wip),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
    SpacerPadding()
}

@Composable
fun Tip(settingsViewModel: SettingsViewModel, message: String) {
    val devMode = settingsViewModel.settings.value.devMode

    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.padding_tip)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        if (devMode) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.dev_mode),
                    fontSize = 6.sp,
                    lineHeight = 1.sp
                )
            }
        }

        Row(
            Modifier
                .padding(dimensionResource(R.dimen.padding_tip))
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Outlined.Info,
                contentDescription = stringResource(R.string.info),
                modifier = Modifier.size(24.dp)
            )
            IconAndTextPadding()
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
    SpacerPadding()
}

@Composable
fun DevBuildTip() {
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.padding_tip)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Row(
            Modifier
                .padding(dimensionResource(R.dimen.padding_tip))
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Outlined.BugReport,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            IconAndTextPadding()
            Text(
                text = stringResource(R.string.debug_build_top_tip),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
    SpacerPadding()
}

@Composable
fun GroupTitle(@StringRes title: Int) = GroupTitle(stringResource(title))

@Composable
fun GroupTitle(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium)
    SpacerPadding()
}

@Composable
fun ScrollableText(text: String) =
    Box(Modifier.horizontalScroll(rememberScrollState())) { Text(text) }


@Composable
fun ScrollableText(@StringRes text: Int) = ScrollableText(stringResource(text))