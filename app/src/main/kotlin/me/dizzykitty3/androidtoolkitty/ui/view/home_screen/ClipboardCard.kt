package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

import android.util.Log
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
import me.dizzykitty3.androidtoolkitty.foundation.util.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.SnackbarUtil
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.ui.component.SpacerPadding

private const val TAG = "ClipboardCard"

@Composable
fun ClipboardCard() {
    CustomCard(
        icon = Icons.Outlined.ContentPasteSearch,
        title = R.string.clipboard
    ) {
        val view = LocalView.current
        val isShowHintText = !SettingsSharedPref.haveOpenedSettingsScreen
        if (isShowHintText) CustomTip(id = R.string.you_can_turn_on_clear_clipboard_on_launch_in_settings_screen)

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
    ClipboardUtil.clear()
    SnackbarUtil.snackbar(view, R.string.clipboard_cleared)
    Log.i(TAG, "Clipboard cleared")
}