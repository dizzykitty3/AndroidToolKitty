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
import me.dizzykitty3.androidtoolkitty.foundation.context_service.ClipboardService
import me.dizzykitty3.androidtoolkitty.foundation.context_service.ToastService
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomTip
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun ClipboardCard() {
    CustomCard(
        icon = Icons.Outlined.ContentPasteSearch,
        title = R.string.clipboard
    ) {
        val context = LocalContext.current

        val isShowHintText = !SettingsViewModel.getHaveOpenedSettingsScreen()
        if (isShowHintText) CustomTip(resId = R.string.you_can_turn_on_clear_clipboard_on_launch_in_settings_screen)

        Button(
            onClick = { onClearClipboardButton(context) },
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Text(text = stringResource(R.string.clear_clipboard))
        }
    }
}

private fun onClearClipboardButton(context: Context) {
    ClipboardService.clear()
    ToastService.toastAndLog(context.getString(R.string.clipboard_cleared))
}