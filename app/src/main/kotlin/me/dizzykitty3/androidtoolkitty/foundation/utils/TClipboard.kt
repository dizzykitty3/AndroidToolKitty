package me.dizzykitty3.androidtoolkitty.foundation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKittyApp.Companion.app

object TClipboard {
    private lateinit var clipboard: ClipboardManager

    private fun clipboardService() {
        clipboard = app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    @JvmStatic
    fun clear() {
        clipboardService()
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
            TToast.toastAndLog(app.getString(R.string.clipboard_cleared_automatically))
        }
    }

    @JvmStatic
    fun copy(text: String) {
        clipboardService()
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        TToast.toast("$text ${app.getString(R.string.copied)}")
    }
}