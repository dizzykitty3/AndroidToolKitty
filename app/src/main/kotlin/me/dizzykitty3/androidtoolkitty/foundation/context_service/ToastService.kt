package me.dizzykitty3.androidtoolkitty.foundation.context_service

import android.content.Context
import android.util.Log
import android.widget.Toast

class ToastService(private val context: Context) {
    companion object {
        private const val TAG = "ToastService"
    }

    fun toast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    fun toastAndLog(logEvent: String) {
        Log.d(TAG, logEvent)
        toast(logEvent)
    }
}