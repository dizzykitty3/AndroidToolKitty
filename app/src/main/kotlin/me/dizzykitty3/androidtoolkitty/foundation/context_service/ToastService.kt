package me.dizzykitty3.androidtoolkitty.foundation.context_service

import android.content.Context
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.foundation.utils.TLog.debugLog

class ToastService(private val context: Context) {
    fun toast(toastText: String) = Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()

    fun toastAndLog(logEvent: String) {
        debugLog(logEvent)
        toast(logEvent)
    }
}