package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomItalicText

@Composable
fun AndroidVersionsCard() {
    CustomCard(
        icon = Icons.Outlined.Android,
        title = R.string.android_versions
    ) {
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                AndroidVersion(text = "Android 15")
                AndroidVersion(text = "Android 14")
                AndroidVersion(text = "Android 13")
                AndroidVersion(text = "Android 12L")
                AndroidVersion(text = "Android 12")
            }

            Column(
                modifier = Modifier.weight(0.6f)
            ) {
                AndroidApiLevelAndName(text = "API 35, VanillaIceCream")
                AndroidApiLevelAndName(text = "API 34, UpsideDownCake")
                AndroidApiLevelAndName(text = "API 33, Tiramisu")
                AndroidApiLevelAndName(text = "API 32, Sv2")
                AndroidApiLevelAndName(text = "API 31, S")
            }
        }

        if (expanded) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(0.4f)
                ) {
                    AndroidVersion(text = "Android 11")
                    AndroidVersion(text = "Android 10")
                    AndroidVersion(text = "Android 9")
                    AndroidVersion(text = "Android 8.1")
                    AndroidVersion(text = "Android 8")
                    AndroidVersion(text = "Android 7.1.1")
                    AndroidVersion(text = "Android 7")
                    AndroidVersion(text = "Android 6")
                    AndroidVersion(text = "Android 5.1")
                    AndroidVersion(text = "Android 5")
                    AndroidVersion(text = "Android 4.4W")
                    AndroidVersion(text = "Android 4.4")
                    AndroidVersion(text = "Android 4.3")
                    AndroidVersion(text = "Android 4.2")
                    AndroidVersion(text = "Android 4.1")
                    AndroidVersion(text = "Android 4.0.3")
                    AndroidVersion(text = "Android 4.0")
                    AndroidVersion(text = "Android 3.2")
                    AndroidVersion(text = "Android 3.1")
                    AndroidVersion(text = "Android 3.0")
                    AndroidVersion(text = "Android 2.3.3")
                    AndroidVersion(text = "Android 2.3")
                    AndroidVersion(text = "Android 2.2")
                    AndroidVersion(text = "Android 2.1")
                }

                Column(
                    modifier = Modifier.weight(0.6f)
                ) {
                    AndroidApiLevelAndName(text = "API 30, R")
                    AndroidApiLevelAndName(text = "API 29, Q")
                    AndroidApiLevelAndName(text = "API 28, Pie")
                    AndroidApiLevelAndName(text = "API 27, Oreo")
                    AndroidApiLevelAndName(text = "API 26, Oreo")
                    AndroidApiLevelAndName(text = "API 25, Nougat")
                    AndroidApiLevelAndName(text = "API 24, Nougat")
                    AndroidApiLevelAndName(text = "API 23, Marshmallow")
                    AndroidApiLevelAndName(text = "API 22, Lollipop")
                    AndroidApiLevelAndName(text = "API 21, Lollipop")
                    AndroidApiLevelAndName(text = "API 20, KitKat Wear")
                    AndroidApiLevelAndName(text = "API 19, KitKat")
                    AndroidApiLevelAndName(text = "API 18, Jelly Bean")
                    AndroidApiLevelAndName(text = "API 17, Jelly Bean")
                    AndroidApiLevelAndName(text = "API 16, Jelly Bean")
                    AndroidApiLevelAndName(text = "API 15, IceCreamSandwich")
                    AndroidApiLevelAndName(text = "API 14, IceCreamSandwich")
                    AndroidApiLevelAndName(text = "API 13, Honeycomb")
                    AndroidApiLevelAndName(text = "API 12, Honeycomb")
                    AndroidApiLevelAndName(text = "API 11, Honeycomb")
                    AndroidApiLevelAndName(text = "API 10, Gingerbread")
                    AndroidApiLevelAndName(text = "API 9, Gingerbread")
                    AndroidApiLevelAndName(text = "API 8, Froyo")
                    AndroidApiLevelAndName(text = "API 7, Eclair")
                }
            }
        }

        TextButton(
            onClick = { expanded = !expanded }
        ) {
            if (!expanded) {
                Text(text = stringResource(R.string.show_more))
            } else {
                Text(text = stringResource(R.string.show_less))
            }
        }
    }
}

@Composable
private fun AndroidApiLevelAndName(text: String) {
    Text(
        text = buildAnnotatedString { CustomItalicText(text = text) },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun AndroidVersion(text: String) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}