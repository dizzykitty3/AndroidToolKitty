package me.dizzykitty3.androidtoolkitty.foundation.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext
import me.dizzykitty3.androidtoolkitty.R

object ClipboardUtil {
    private var clipboard: ClipboardManager =
        appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    fun clear() {
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
        }
    }

    fun check(): Boolean {
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
            return true
        }
        return false
    }

    fun copy(view: View, text: String) {
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        SnackbarUtil.snackbar(view, "$text ${appContext.getString(R.string.copied)}")
    }
}