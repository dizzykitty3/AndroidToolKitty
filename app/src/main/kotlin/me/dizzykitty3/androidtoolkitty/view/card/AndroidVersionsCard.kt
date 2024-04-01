package me.dizzykitty3.androidtoolkitty.view.card

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
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomAndroidApiLevelAndName
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSingleLineText

@Composable
fun AndroidVersionsCard() {
    CustomCard(
        icon = Icons.Outlined.Android,
        title = R.string.android_versions
    ) {
        val c = LocalContext.current

        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                CustomSingleLineText(text = "Android 15")
                CustomSingleLineText(text = "Android 14")
                CustomSingleLineText(text = "Android 13")
                CustomSingleLineText(text = "Android 12L")
                CustomSingleLineText(text = "Android 12")
            }

            Column(
                modifier = Modifier.weight(0.6f)
            ) {
                CustomAndroidApiLevelAndName(text = "VanillaIceCream")
                CustomAndroidApiLevelAndName(text = "API 34, UpsideDownCake")
                CustomAndroidApiLevelAndName(text = "API 33, Tiramisu")
                CustomAndroidApiLevelAndName(text = "API 32, Sv2")
                CustomAndroidApiLevelAndName(text = "API 31, S")
            }
        }

        if (expanded) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(0.4f)
                ) {
                    CustomSingleLineText(text = "Android 11")
                    CustomSingleLineText(text = "Android 10")
                    CustomSingleLineText(text = "Android 9")
                    CustomSingleLineText(text = "Android 8.1")
                    CustomSingleLineText(text = "Android 8")
                    CustomSingleLineText(text = "Android 7.1.1")
                    CustomSingleLineText(text = "Android 7")
                    CustomSingleLineText(text = "Android 6")
                    CustomSingleLineText(text = "Android 5.1")
                    CustomSingleLineText(text = "Android 5")
                    CustomSingleLineText(text = "Android 4.4W")
                    CustomSingleLineText(text = "Android 4.4")
                    CustomSingleLineText(text = "Android 4.3")
                    CustomSingleLineText(text = "Android 4.2")
                    CustomSingleLineText(text = "Android 4.1")
                    CustomSingleLineText(text = "Android 4.0.3")
                    CustomSingleLineText(text = "Android 4.0")
                    CustomSingleLineText(text = "Android 3.2")
                    CustomSingleLineText(text = "Android 3.1")
                    CustomSingleLineText(text = "Android 3.0")
                    CustomSingleLineText(text = "Android 2.3.3")
                    CustomSingleLineText(text = "Android 2.3")
                    CustomSingleLineText(text = "Android 2.2")
                    CustomSingleLineText(text = "Android 2.1")
                }

                Column(
                    modifier = Modifier.weight(0.6f)
                ) {
                    CustomAndroidApiLevelAndName(text = "API 30, R")
                    CustomAndroidApiLevelAndName(text = "API 29, Q")
                    CustomAndroidApiLevelAndName(text = "API 28, Pie")
                    CustomAndroidApiLevelAndName(text = "API 27, Oreo")
                    CustomAndroidApiLevelAndName(text = "API 26, Oreo")
                    CustomAndroidApiLevelAndName(text = "API 25, Nougat")
                    CustomAndroidApiLevelAndName(text = "API 24, Nougat")
                    CustomAndroidApiLevelAndName(text = "API 23, Marshmallow")
                    CustomAndroidApiLevelAndName(text = "API 22, Lollipop")
                    CustomAndroidApiLevelAndName(text = "API 21, Lollipop")
                    CustomAndroidApiLevelAndName(text = "API 20, KitKat Wear")
                    CustomAndroidApiLevelAndName(text = "API 19, KitKat")
                    CustomAndroidApiLevelAndName(text = "API 18, Jelly Bean")
                    CustomAndroidApiLevelAndName(text = "API 17, Jelly Bean")
                    CustomAndroidApiLevelAndName(text = "API 16, Jelly Bean")
                    CustomAndroidApiLevelAndName(text = "API 15, IceCreamSandwich")
                    CustomAndroidApiLevelAndName(text = "API 14, IceCreamSandwich")
                    CustomAndroidApiLevelAndName(text = "API 13, Honeycomb")
                    CustomAndroidApiLevelAndName(text = "API 12, Honeycomb")
                    CustomAndroidApiLevelAndName(text = "API 11, Honeycomb")
                    CustomAndroidApiLevelAndName(text = "API 10, Gingerbread")
                    CustomAndroidApiLevelAndName(text = "API 9, Gingerbread")
                    CustomAndroidApiLevelAndName(text = "API 8, Froyo")
                    CustomAndroidApiLevelAndName(text = "API 7, Eclair")
                }
            }
        }

        TextButton(
            onClick = { expanded = !expanded }
        ) {
            if (!expanded) {
                Text(text = c.getString(R.string.show_more))
            } else {
                Text(text = c.getString(R.string.show_less))
            }
        }
    }
}