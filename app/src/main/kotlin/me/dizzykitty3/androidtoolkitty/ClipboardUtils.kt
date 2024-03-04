package me.dizzykitty3.androidtoolkitty

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.Utils.showToastAndRecordLog

class ClipboardUtils(private val context: Context?) {
    private var clipboard: ClipboardManager? = null

    private fun initService() {
        checkContextNullSafety()
        getSystemClipboardService()
    }

    private fun checkContextNullSafety() {
        checkNotNull(context) { "Context has not been initialized. Please call init() first." }
    }

    private fun getSystemClipboardService() {
        clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun clearClipboard() {
        initService()
        if (clipboard!!.hasPrimaryClip()) {
            clipboard!!.clearPrimaryClip()
            showToastAndRecordLog("clipboard cleared automatically")
        }
    }

    fun copyTextToClipboard(text: String) {
        initService()
        val clip = ClipData.newPlainText("label", text)
        clipboard!!.setPrimaryClip(clip)
    }
}