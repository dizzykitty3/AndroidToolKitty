package me.dizzykitty3.androidtoolkitty.foundation.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app
import me.dizzykitty3.androidtoolkitty.R

object ClipboardUtil {
    private var clipboard: ClipboardManager =
        app.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

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

    fun copy(text: String) {
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        SnackbarUtil.snackbar("$text ${app.applicationContext.getString(R.string.copied)}")
    }
}