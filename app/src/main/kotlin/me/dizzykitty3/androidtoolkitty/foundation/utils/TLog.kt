package me.dizzykitty3.androidtoolkitty.foundation.utils

import android.util.Log

object TLog {
    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun debugLog(message: String) {
        Log.d("me.dizzykitty3.androidtoolkitty", message)
    }
}