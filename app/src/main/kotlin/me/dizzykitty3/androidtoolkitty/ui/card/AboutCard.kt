package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.util.Utils

@Composable
fun AboutCard() {
    CustomCard(title = "About") {
        val sourceCodeUrl = "https://github.com/dizzykitty3/android_toolkitty"
        Row {
            Icon(
                imageVector = Icons.Outlined.Code,
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "source code on GitHub",
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    Utils.openUrl(sourceCodeUrl)
                }
            )
        }
    }
}