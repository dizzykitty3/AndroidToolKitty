package me.dizzykitty3.androidtoolkitty.foundation.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKittyApp.Companion.app

object ClipboardUtil {
    private const val TAG = "ClipboardUtil"
    private lateinit var clipboard: ClipboardManager

    private fun clipboardService() {
        clipboard = app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    @JvmStatic
    fun clear() {
        clipboardService()
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
            ToastUtil.toastAndLog(TAG,app.getString(R.string.clipboard_cleared_automatically))
        }
    }

    @JvmStatic
    fun copy(text: String) {
        clipboardService()
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        ToastUtil.toast("$text ${app.getString(R.string.copied)}")
    }
}