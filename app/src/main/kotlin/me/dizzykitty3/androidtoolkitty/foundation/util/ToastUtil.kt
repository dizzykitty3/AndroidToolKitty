package me.dizzykitty3.androidtoolkitty.foundation.util

import android.util.Log
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

object ToastUtil {
    @JvmStatic
    fun toast(message: String) = Toast.makeText(app, message, Toast.LENGTH_SHORT).show()

    @JvmStatic
    fun toastAndLog(tag:String,logEvent: String) {
        Log.i(tag, logEvent)
        toast(logEvent)
    }
}