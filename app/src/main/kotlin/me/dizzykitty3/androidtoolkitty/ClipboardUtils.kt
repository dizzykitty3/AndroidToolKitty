package me.dizzykitty3.androidtoolkitty

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardUtils(private val context: Context) {
    fun clearClipboard() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
            Utils.showToastAndRecordLog(context, "clipboard cleared automatically")
        }
    }

    fun copyTextToClipboard(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }
}