package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.util.Utils.onClearClipboardButton

@Composable
fun ClipboardCard() {
    CustomCard(
        title = LocalContext.current.getString(R.string.clipboard),
        isExpand = true
    ) {
        Button(
            onClick = {
                onClearClipboardButton()
            }
        ) {
            Text(text = LocalContext.current.getString(R.string.clear_clipboard))
        }
    }
}
