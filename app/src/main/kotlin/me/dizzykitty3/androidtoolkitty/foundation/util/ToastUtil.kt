package me.dizzykitty3.androidtoolkitty.foundation.util

import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

object ToastUtil {
    @JvmStatic
    fun toast(message: String) = Toast.makeText(app, message, Toast.LENGTH_SHORT).show()

    @JvmStatic
    fun toast(message: Int) = toast(app.applicationContext.getString(message))
}