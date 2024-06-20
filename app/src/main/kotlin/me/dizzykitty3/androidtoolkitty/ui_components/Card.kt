package me.dizzykitty3.androidtoolkitty.ui_components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun Card(
    icon: ImageVector? = null,
    @StringRes title: Int,
    content: @Composable () -> Unit
) {
    Card(
        icon = icon,
        title = stringResource(id = title),
        content = content
    )
}

@Composable
fun Card(
    icon: ImageVector? = null,
    title: String,
    content: @Composable () -> Unit
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card_content))

        Column(modifier = cardPadding) {
            if (icon == null) {
                CardTitle(title = title)
                CardContent { content() }
            } else {
                Row {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                    IconAndTextPadding()
                    CardTitle(title = title)
                }
                CardContent { content() }
            }
        }
    }
    CardSpacePadding()
}

@Composable
private fun CardTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun CardContent(content: @Composable () -> Unit) {
    Column {
        SpacerPadding()
        SpacerPadding()
        Column { content() }
    }
}