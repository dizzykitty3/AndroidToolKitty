package me.dizzykitty3.androidtoolkitty.util

import android.content.Context
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.Actions
import java.util.Objects

class ToastUtils(private val context: Context) {
    private lateinit var currentToast: Toast

    fun showToast(toastText: String?) {
        if (Objects.nonNull(currentToast)) {
            currentToast.cancel()
        }
        currentToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT)
        currentToast.show()
    }

    fun showToastAndRecordLog(logEvent: String) {
        Actions.debugLog(logEvent)
        showToast(logEvent)
    }
}