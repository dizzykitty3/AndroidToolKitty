package me.dizzykitty3.androidtoolkitty.common.util

import android.content.Context
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils.debugLog
import java.util.Objects

class ToastUtils(private val context: Context) {
    private var currentToast: Toast? = null

    fun showToast(toastText: String?) {
        if (Objects.nonNull(currentToast)) {
            currentToast?.cancel()
        }
        currentToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT)
        currentToast?.show()
    }

    fun showToastAndRecordLog(logEvent: String) {
        debugLog(logEvent)
        showToast(logEvent)
    }
}