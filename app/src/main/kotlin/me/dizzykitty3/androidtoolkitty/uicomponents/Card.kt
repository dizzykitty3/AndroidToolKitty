package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
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
    @StringRes title: Int,
    icon: ImageVector? = null,
    hasShowMore: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        title = stringResource(title),
        icon = icon,
        hasShowMore = hasShowMore,
        onClick = onClick,
        content = content
    )
}

@Composable
fun Card(
    title: String,
    icon: ImageVector? = null,
    hasShowMore: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_card_content_top_and_bottom),
                    bottom = dimensionResource(R.dimen.padding_card_content_top_and_bottom),
                    start = dimensionResource(R.dimen.padding_card_content),
                    end = dimensionResource(R.dimen.padding_card_content)
                )
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Column {
                        if (icon == null) {
                            GroupTitleNoColor(title)
                        } else {
                            SpacerPadding()
                            Row {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                IconAndTextPadding()
                                CardTitle(title)
                            }
                            SpacerPadding()
                        }
                    }
                }
                if (hasShowMore && onClick != null) {
                    FilledTonalButton(onClick) {
                        Icon(
                            imageVector = Icons.Outlined.MoreHoriz,
                            contentDescription = null
                        )
                    }
                }
            }
            SpacerPadding()
            content()
        }
    }
    CardSpacePadding()
}

@Composable
private fun CardTitle(title: String) =
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )