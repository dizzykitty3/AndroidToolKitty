package me.dizzykitty3.androidtoolkitty.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.util.Utils.showToast
import me.dizzykitty3.androidtoolkitty.util.Utils.showToastAndRecordLog

class ClipboardUtils(private val context: Context) {
    private lateinit var clipboard: ClipboardManager

    private fun getSystemClipboardService() {
        clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun clearClipboard() {
        getSystemClipboardService()
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
            showToastAndRecordLog(context.getString(R.string.clipboard_cleared_automatically))
        }
    }

    fun copyTextToClipboard(text: String) {
        getSystemClipboardService()
        val clip = ClipData.newPlainText("copied from Android ToolKitty", text)
        clipboard.setPrimaryClip(clip)
        showToast("$text " + context.getString(R.string.copied))
    }
}