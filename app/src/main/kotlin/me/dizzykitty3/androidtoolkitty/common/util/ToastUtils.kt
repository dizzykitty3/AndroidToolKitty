package me.dizzykitty3.androidtoolkitty.common.util

import android.content.Context
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils.debugLog

class ToastUtils(private val context: Context) {
    fun showToast(toastText: String) = Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()

    fun showToastAndRecordLog(logEvent: String) {
        debugLog(logEvent)
        showToast(logEvent)
    }
}