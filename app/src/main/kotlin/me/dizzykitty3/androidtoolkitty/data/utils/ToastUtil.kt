package me.dizzykitty3.androidtoolkitty.data.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import me.dizzykitty3.androidtoolkitty.data.utils.SnackbarUtil.showSnackbar

object ToastUtil {
    /**
     * [NOT RECOMMENDED. Use Snackbar instead.](https://developer.android.com/guide/topics/ui/notifiers/toasts#alternatives_to_using_toasts)
     * @see SnackbarUtil.showSnackbar
     */
    fun Context.showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    /**
     * [NOT RECOMMENDED. Use Snackbar instead.](https://developer.android.com/guide/topics/ui/notifiers/toasts#alternatives_to_using_toasts)
     * @see SnackbarUtil.showSnackbar
     */
    fun Context.showToast(@StringRes message: Int) = this.showToast(this.getString(message))
}