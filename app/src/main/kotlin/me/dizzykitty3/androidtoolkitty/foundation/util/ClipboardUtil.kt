package me.dizzykitty3.androidtoolkitty.foundation.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app
import me.dizzykitty3.androidtoolkitty.R

object ClipboardUtil {
    private lateinit var clipboard: ClipboardManager

    private fun clipboardService() {
        clipboard = app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    @JvmStatic
    fun clear() {
        clipboardService()
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
        }
    }

    @JvmStatic
    fun check(): Boolean {
        clipboardService()
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip()
            return true
        }
        return false
    }

    @JvmStatic
    fun copy(text: String) {
        clipboardService()
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        ToastUtil.toast("$text ${app.getString(R.string.copied)}")
    }
}