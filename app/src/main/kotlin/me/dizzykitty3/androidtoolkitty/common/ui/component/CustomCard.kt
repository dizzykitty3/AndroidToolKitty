package me.dizzykitty3.androidtoolkitty.common.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun CustomCard(
    icon: ImageVector,
    title: String,
    id: String,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card_content))
        Column(
            modifier = cardPadding
        ) {
            val c = LocalContext.current
            val mExpanded = SettingsViewModel().getCardExpandedState(c, id)
            var expanded by remember { mutableStateOf(mExpanded) }
            Row {
                Icon(
                    imageVector = icon, // Custom Icon here
                    contentDescription = null,
                    modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    )
                )
                CustomIconAndTextPadding()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expanded = !expanded
                            SettingsViewModel().saveCardExpandedState(c, id, expanded)
                        },
                    text = title, // Custom title here
                    style = TextStyle.Default.copy(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        fontSize = 22.sp,
                        lineHeight = 28.0.sp,
                        letterSpacing = 0.0.sp
                    )
                )
            }
            AnimatedVisibility(expanded) {
                Column {
                    CustomSpacerPadding()
                    Column {
                        val lifecycleOwner = LocalLifecycleOwner.current
                        val focusManager = LocalFocusManager.current
                        DisposableEffect(key1 = lifecycleOwner) {
                            onDispose {
                                focusManager.clearFocus()
                            }
                        }
                        content() // Custom contents here
                    }
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
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card_content))
        Column(
            modifier = cardPadding
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title, // Custom title here
                style = TextStyle.Default.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp,
                    lineHeight = 28.0.sp,
                    letterSpacing = 0.0.sp
                )
            )
            Column {
                CustomSpacerPadding()
                Column {
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val focusManager = LocalFocusManager.current
                    DisposableEffect(key1 = lifecycleOwner) {
                        onDispose {
                            focusManager.clearFocus()
                        }
                    }
                    content() // Custom contents here
                }
            }
        }
    }
}