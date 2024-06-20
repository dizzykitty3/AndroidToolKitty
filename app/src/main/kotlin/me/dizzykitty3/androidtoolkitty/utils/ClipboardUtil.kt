package me.dizzykitty3.androidtoolkitty.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.annotation.CheckResult
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext

object ClipboardUtil {
    private var clipboard =
        appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    @SuppressLint("NewApi")
    fun clear() {
        if (OSVersion.android9()) {
            clipboard.clearPrimaryClip()
            return
        }
        copy("")
    }

    @SuppressLint("NewApi")
    @CheckResult
    fun check(): Boolean {
        if (!clipboard.hasPrimaryClip()) return false
        if (OSVersion.android9()) {
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