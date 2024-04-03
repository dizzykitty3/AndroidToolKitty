package me.dizzykitty3.androidtoolkitty.foundation.context_service

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.R

class ClipboardService(private val context: Context) {
    private lateinit var clipboard: ClipboardManager

    private fun clipboardService() {
        clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun clear() {
        clipboardService()
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
            ToastService(context).toastAndLog(context.getString(R.string.clipboard_cleared_automatically))
        }
    }

    fun copy(text: String) {
        clipboardService()
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        ToastService(context).toast("$text ${context.getString(R.string.copied)}")
    }
}