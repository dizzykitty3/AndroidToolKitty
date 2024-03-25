package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomItalicText

@Composable
fun AndroidVersionsCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.Outlined.Android,
        title = c.getString(R.string.android_versions)
    ) {
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 15")
                CustomItalicText(text = " / VanillaIceCream")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 14")
                CustomItalicText(text = " / API 34, UpsideDownCake")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 13")
                CustomItalicText(text = " / API 33, Tiramisu")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 12L")
                CustomItalicText(text = " / API 32, Sv2")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 12")
                CustomItalicText(text = " / API 31, S")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 11")
                CustomItalicText(text = " / API 30, R")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 10")
                CustomItalicText(text = " / API 29, Q")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 9")
                CustomItalicText(text = " / API 28, Pie")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 8.1")
                CustomItalicText(text = " / API 27, Oreo")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 8")
                CustomItalicText(text = " / API 26, Oreo")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 7.1.1")
                CustomItalicText(text = " / API 25, Nougat")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 7")
                CustomItalicText(text = " / API 24, Nougat")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 6")
                CustomItalicText(text = " / API 23, Marshmallow")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 5.1")
                CustomItalicText(text = " / API 22, Lollipop")
            })
        }
        Row {
            Text(text = buildAnnotatedString {
                Text(text = "Android 5")
                CustomItalicText(text = " / API 21, Lollipop")
            })
        }
    }
}