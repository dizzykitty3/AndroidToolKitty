package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref

@Composable
fun Card(
    @StringRes title: Int,
    icon: ImageVector? = null,
    content: @Composable () -> Unit
) {
    Card(
        title = stringResource(title),
        icon = icon,
        content = content
    )
}

@Composable
fun Card(
    title: String,
    icon: ImageVector? = null,
    content: @Composable () -> Unit
) {
    val testLayout = SettingsSharedPref.testLayout

    // TODO
    if (testLayout) {
        Column(Modifier.fillMaxWidth()) {
            if (icon == null) {
                CardTitle(title)
            } else {
                Row {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    IconAndTextPadding()
                    CardTitle(title)
                }
            }
            SpacerPadding()
            SpacerPadding()
            content()
        }
        CardSpacePadding()
        CardSpacePadding()
    } else {
        ElevatedCard(Modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_card_content))
            ) {
                if (icon == null) {
                    CardTitle(title)
                } else {
                    Row {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        IconAndTextPadding()
                        CardTitle(title)
                    }
                }
                SpacerPadding()
                SpacerPadding()
                content()
            }
        }
        CardSpacePadding()
    }
}

@Composable
private fun CardTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun CardShowMore(onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        Spacer(Modifier.weight(1f))
        TextButton(onClick) {
            Text(
                stringResource(R.string.show_more),
                textAlign = TextAlign.End
            )
        }
    }
}