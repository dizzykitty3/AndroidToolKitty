package me.dizzykitty3.androidtoolkitty.foundation.util

import android.widget.Toast
import androidx.annotation.StringRes
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

object ToastUtil {
    /**
     * NOT RECOMMENDED. Use Snackbar instead.
     * @see SnackbarUtil.snackbar
     */
    @JvmStatic
    fun toast(message: String) =
        Toast.makeText(app.applicationContext, message, Toast.LENGTH_SHORT).show()

    /**
     * NOT RECOMMENDED. Use Snackbar instead.
     * @see SnackbarUtil.snackbar
     */
    @JvmStatic
    fun toast(@StringRes message: Int) = toast(app.applicationContext.getString(message))
}