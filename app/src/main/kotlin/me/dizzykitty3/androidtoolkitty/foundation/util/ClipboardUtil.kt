package me.dizzykitty3.androidtoolkitty.foundation.util

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext

object ClipboardUtil {
    private var clipboard =
        appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    @SuppressLint("NewApi")
    fun clear() {
        if (!clipboard.hasPrimaryClip()) return
        if (OsVersion.android9()) {
            clipboard.clearPrimaryClip()
            return
        }
        copy("")
    }

    @SuppressLint("NewApi")
    fun check(): Boolean {
        if (!clipboard.hasPrimaryClip()) return false
        if (OsVersion.android9()) {
            clipboard.clearPrimaryClip()
            return true
        }
        copy("")
        return true
    }

    fun copy(text: String) {
        val clip = ClipData.newPlainText("", text)
        clipboard.setPrimaryClip(clip)
    }
}