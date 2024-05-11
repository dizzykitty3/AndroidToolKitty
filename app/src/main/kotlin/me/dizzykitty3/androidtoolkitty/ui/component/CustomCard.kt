package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun CustomCard(
    icon: ImageVector? = null,
    @StringRes title: Int,
    content: @Composable () -> Unit
) {
    CustomCard(
        icon = icon,
        title = stringResource(id = title),
        content = content
    )
}

@Composable
fun CustomCard(
    icon: ImageVector? = null,
    title: String,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card_content))

        Column(
            modifier = cardPadding
        ) {
            if (icon == null) {
                CardTitle(title = title)
                CardContent { content() }
            } else {
                Row {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    CustomIconAndTextPadding()
                    CardTitle(title = title)
                }
                CardContent { content() }
            }
        }
    }
    CustomCardSpacePadding()
}

@Composable
private fun CardTitle(
    title: String
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        style = TextStyle.Default.copy(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.0.sp,
            letterSpacing = 0.0.sp
        )
    )
}

@Composable
private fun CardContent(
    content: @Composable () -> Unit
) {
    Column {
        CustomSpacerPadding()
        CustomSpacerPadding()
        Column {
            content()
        }
    }
}