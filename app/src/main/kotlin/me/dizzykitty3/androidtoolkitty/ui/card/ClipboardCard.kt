package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.util.Utils.onClearClipboardButton

@Composable
fun ClipboardCard() {
    CustomCard(title = LocalContext.current.getString(R.string.clipboard)) {
        var checked by remember { mutableStateOf(true) }
        Button(
            onClick = {
                onClearClipboardButton()
            }
        ) {
            Text(text = LocalContext.current.getString(R.string.clear_clipboard))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "test switch")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = checked,
                onCheckedChange = { checked = it }
            )
        }
    }
}
