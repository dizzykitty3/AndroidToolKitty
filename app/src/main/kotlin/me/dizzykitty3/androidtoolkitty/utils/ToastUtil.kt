package me.dizzykitty3.androidtoolkitty.utils

import android.widget.Toast
import androidx.annotation.StringRes
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.snackbar

object ToastUtil {
    /**
     * [NOT RECOMMENDED. Use Snackbar instead.](https://developer.android.com/guide/topics/ui/notifiers/toasts#alternatives_to_using_toasts)
     * @see SnackbarUtil.snackbar
     */
    fun show(message: String) = Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()

    /**
     * [NOT RECOMMENDED. Use Snackbar instead.](https://developer.android.com/guide/topics/ui/notifiers/toasts#alternatives_to_using_toasts)
     * @see SnackbarUtil.snackbar
     */
    fun show(@StringRes message: Int) = show(appContext.getString(message))
}