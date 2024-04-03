package me.dizzykitty3.androidtoolkitty.foundation.ui_component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun CustomCard(
    hasIcon: Boolean = true,
    icon: ImageVector?,
    title: Int,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card_content))

        Column(
            modifier = cardPadding
        ) {
            if (!hasIcon) {
                CardTitle(resId = title)
                CardContentColumn { content() }
            } else {
                Row {
                    Icon(
                        imageVector = icon!!,
                        contentDescription = null,
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    CustomIconAndTextPadding()
                    CardTitle(resId = title)
                }
                CardContentColumn { content() }
            }
        }
    }
    CustomSpacerPadding()
}

@Composable
fun CustomCardNoIcon(
    title: Int,
    content: @Composable () -> Unit
) {
    CustomCard(
        icon = null,
        title = title,
        content = content,
        hasIcon = false
    )
}

@Composable
private fun CardContentColumn(
    content: @Composable () -> Unit
) {
    Column {
        CustomSpacerPadding()
        CustomSpacerPadding()
        Column {
            val lifecycleOwner = LocalLifecycleOwner.current
            val focusManager = LocalFocusManager.current

            DisposableEffect(key1 = lifecycleOwner) {
                onDispose {
                    focusManager.clearFocus()
                }
            }
            content()
        }
    }
}