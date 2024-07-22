package me.dizzykitty3.androidtoolkitty.home

import android.view.View
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.ContentPasteSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.uicomponents.Tip
import me.dizzykitty3.androidtoolkitty.utils.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import timber.log.Timber

@Composable
fun Clipboard(settingsViewModel: SettingsViewModel) {
    Card(R.string.clipboard, Icons.Outlined.ContentPasteSearch) {
        val view = LocalView.current
        val isShowHintText = !settingsViewModel.settings.value.haveOpenedSettings
        val devMode = settingsViewModel.settings.value.devMode
        if (isShowHintText || devMode)
            Tip(
                settingsViewModel,
                R.string.you_can_turn_on_clear_clipboard_on_launch_in_settings_screen
            )

        OutlinedButton({
            view.hapticFeedback()
            onClearClipboardButton(view)
        }) {
            Icon(
                imageVector = Icons.Outlined.ClearAll,
                contentDescription = stringResource(id = R.string.clear_clipboard),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SpacerPadding()
            Text(stringResource(R.string.clear_clipboard))
        }
    }
}

private fun onClearClipboardButton(view: View) {
    val cleared = ClipboardUtil.clear()
    Timber.i("Clipboard cleared")
    view.showSnackbar(if (cleared) R.string.clipboard_cleared else R.string.clipboard_is_empty)
}