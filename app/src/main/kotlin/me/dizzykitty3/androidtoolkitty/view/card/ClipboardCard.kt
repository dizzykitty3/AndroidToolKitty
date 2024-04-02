package me.dizzykitty3.androidtoolkitty.view.card

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPasteSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.common.util.ClipboardUtils
import me.dizzykitty3.androidtoolkitty.common.util.ToastUtils
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun ClipboardCard() {
    CustomCard(
        icon = Icons.Outlined.ContentPasteSearch,
        title = R.string.clipboard
    ) {
        val context = LocalContext.current

        val isShowHintText = !SettingsViewModel().getHaveOpenedSettingsScreen(context)
        if (isShowHintText) CustomTip(resId = R.string.you_can_turn_on_clear_clipboard_on_launch_in_settings_screen)

        Button(
            onClick = { onClearClipboardButton(context) },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Text(text = stringResource(R.string.clear_clipboard))
        }
    }
}

private fun onClearClipboardButton(c: Context) {
    ClipboardUtils(c).clearClipboard()
    ToastUtils(c).showToastAndRecordLog(c.getString(R.string.clipboard_cleared))
}