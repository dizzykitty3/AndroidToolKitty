package me.dizzykitty3.androidtoolkitty.common.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.R

class ClipboardUtils(private val c: Context) {
    private lateinit var clipboard: ClipboardManager

    private fun getSystemClipboardService() {
        clipboard = c.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun clearClipboard() {
        getSystemClipboardService()
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
            ToastUtils(c).showToastAndRecordLog(c.getString(R.string.clipboard_cleared_automatically))
        }
    }

    fun copyTextToClipboard(text: String) {
        getSystemClipboardService()
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        ToastUtils(c).showToast("$text ${c.getString(R.string.copied)}")
    }
}