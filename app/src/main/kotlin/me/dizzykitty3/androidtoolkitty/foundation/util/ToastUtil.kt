package me.dizzykitty3.androidtoolkitty.foundation.util

import android.widget.Toast
import androidx.annotation.StringRes
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext

object ToastUtil {
    /**
     * NOT RECOMMENDED. Use Snackbar instead.
     * @see SnackbarUtil.snackbar
     */
    fun toast(message: String) =
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()

    /**
     * NOT RECOMMENDED. Use Snackbar instead.
     * @see SnackbarUtil.snackbar
     */
    fun toast(@StringRes message: Int) = toast(appContext.getString(message))
}