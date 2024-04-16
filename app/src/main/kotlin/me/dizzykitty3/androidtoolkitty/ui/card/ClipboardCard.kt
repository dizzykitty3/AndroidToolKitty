package me.dizzykitty3.androidtoolkitty.ui.card

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.ContentPasteSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomIconAndTextPadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.foundation.utils.TClipboard
import me.dizzykitty3.androidtoolkitty.foundation.utils.TToast

@Composable
fun ClipboardCard() {
    CustomCard(
        icon = Icons.Outlined.ContentPasteSearch,
        title = R.string.clipboard
    ) {
        val context = LocalContext.current

        val isShowHintText = !SettingsSharedPref.getHaveOpenedSettingsScreen()
        if (isShowHintText) CustomTip(resId = R.string.you_can_turn_on_clear_clipboard_on_launch_in_settings_screen)

        Button(
            onClick = { onClearClipboardButton(context) },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ClearAll,
                contentDescription = stringResource(id = R.string.clear_clipboard),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            CustomIconAndTextPadding()
            Text(text = stringResource(R.string.clear_clipboard))
        }
    }
}

private fun onClearClipboardButton(context: Context) {
    TClipboard.clear()
    TToast.toastAndLog(context.getString(R.string.clipboard_cleared))
}