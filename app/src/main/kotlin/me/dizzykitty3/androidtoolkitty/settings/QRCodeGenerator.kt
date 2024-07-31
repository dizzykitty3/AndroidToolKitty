package me.dizzykitty3.androidtoolkitty.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.ClearInput
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.uicomponents.WIPTip
import androidx.compose.ui.Alignment.Companion as Alignment1

@Composable
fun QRCodeGenerator() {
    Screen {
        Card("QR Code generator") {
            var input by remember { mutableStateOf("") }
            var instantOption by remember { mutableStateOf(false) }

            WIPTip()

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = input,
                onValueChange = { input = it },
                label = { Text(text = "Contents") },
                trailingIcon = {
                    ClearInput(input) {
                        input = ""
                    }
                }
            )

            SpacerPadding()

            Row(
                verticalAlignment = Alignment1.CenterVertically,
                modifier = Modifier.clickable { instantOption = !instantOption }
            ) {
                Text(
                    text = "Generate QR Code instantly",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = instantOption,
                    onCheckedChange = { instantOption = it }
                )
            }

            if (!instantOption) {
                TextButton(
                    onClick = { }
                ) {
                    Text(text = "Generate")
                }
            }
            GroupDivider()
            QRPic()
        }
    }
}

@Composable
private fun QRPic(
    content: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.Center
    ) {
        if (content.isNullOrBlank()) {
            Icon(
                imageVector = Icons.Outlined.QrCode2,
                contentDescription = "QR Code",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (content.isNullOrBlank()) {
            Text(
                text = "Generated QR Code will be shown here ...",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}