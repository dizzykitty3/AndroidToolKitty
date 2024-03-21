package me.dizzykitty3.androidtoolkitty.common.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun CustomCard(
    isExpandable: Boolean = true,
    icon: ImageVector?,
    id: String?,
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
            if (!isExpandable) {
                CardTitle(title = title)
                CardContentColumn { content() }
            } else {
                // If a card is expandable, param id and icon must not be null
                val c = LocalContext.current
                val mExpanded = SettingsViewModel().getCardExpandedState(c, id!!)
                var expanded by remember { mutableStateOf(mExpanded) }
                Row {
                    Icon(
                        imageVector = icon!!,
                        contentDescription = null,
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    CustomIconAndTextPadding()
                    Box(
                        modifier = Modifier.clickable {
                            expanded = !expanded
                            SettingsViewModel().saveCardExpandedState(c, id, expanded)
                        }
                    ) {
                        CardTitle(title = title)
                    }
                }
                AnimatedVisibility(expanded) {
                    CardContentColumn { content() }
                }
            }
        }
    }
}

@Composable
fun CustomStaticCard(
    title: String,
    content: @Composable () -> Unit
) {
    CustomCard(
        icon = null,
        title = title,
        id = null,
        content = content,
        isExpandable = false
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