package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

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
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.ui.component.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil
import timber.log.Timber

@Composable
fun ClipboardCard() {
    CustomCard(
        icon = Icons.Outlined.ContentPasteSearch,
        titleRes = R.string.clipboard
    ) {
        val view = LocalView.current
        val isShowHintText = !SettingsSharedPref.haveOpenedSettingsScreen
        val uiTesting = SettingsSharedPref.uiTesting
        if (isShowHintText || uiTesting) CustomTip(id = R.string.you_can_turn_on_clear_clipboard_on_launch_in_settings_screen)

        OutlinedButton(
            onClick = { onClearClipboardButton(view) }
        ) {
            Icon(
                imageVector = Icons.Outlined.ClearAll,
                contentDescription = stringResource(id = R.string.clear_clipboard),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SpacerPadding()
            Text(text = stringResource(R.string.clear_clipboard))
        }
    }
}

private fun onClearClipboardButton(view: View) {
    val cleared = ClipboardUtil.check()
    Timber.i("Clipboard cleared")
    if (cleared)
        SnackbarUtil.snackbar(view, R.string.clipboard_cleared)
    else
        SnackbarUtil.snackbar(view, R.string.clipboard_is_empty)
}