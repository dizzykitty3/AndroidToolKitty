package me.dizzykitty3.androidtoolkitty.foundation.util

import android.widget.Toast
import androidx.annotation.StringRes
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext

object ToastUtil {
    /**
     * [NOT RECOMMENDED. Use Snackbar instead.](https://developer.android.com/guide/topics/ui/notifiers/toasts#alternatives_to_using_toasts)
     * @see SnackbarUtil.snackbar
     */
    fun toast(message: String) =
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()

    /**
     * [NOT RECOMMENDED. Use Snackbar instead.](https://developer.android.com/guide/topics/ui/notifiers/toasts#alternatives_to_using_toasts)
     * @see SnackbarUtil.snackbar
     */
    fun toast(@StringRes messageRes: Int) =
        toast(appContext.getString(messageRes))
}