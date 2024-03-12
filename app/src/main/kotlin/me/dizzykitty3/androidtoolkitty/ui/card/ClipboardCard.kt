package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPasteSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.Actions
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding

@Composable
fun ClipboardCard() {
    CustomCard(
        icon = Icons.Outlined.ContentPasteSearch,
        title = LocalContext.current.getString(R.string.clipboard),
        isExpand = true
    ) {
        val c = LocalContext.current
        Text(
            text = c.getString(R.string.temp1)
        )
        CustomSpacerPadding()
        Button(
            onClick = {
                Actions.onClearClipboardButton()
            },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Text(
                text = LocalContext.current.getString(R.string.clear_clipboard)
            )
        }
    }
}
