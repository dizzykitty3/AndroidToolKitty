package me.dizzykitty3.androidtoolkitty.utils

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext

object SnackbarUtil {
    fun snackbar(view: View, message: String?) =
        if (message == null) {
        } else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        }

    fun snackbar(view: View, @StringRes messageRes: Int) =
        Snackbar.make(view, appContext.getString(messageRes), Snackbar.LENGTH_SHORT).show()

    fun snackbar(
        view: View,
        @StringRes messageRes: Int,
        @StringRes buttonTextRes: Int,
        buttonColor: Int,
        buttonClickListener: View.OnClickListener
    ) =
        Snackbar.make(view, appContext.getString(messageRes), Snackbar.LENGTH_LONG)
            .setAction(appContext.getString(buttonTextRes), buttonClickListener)
            .setActionTextColor(buttonColor)
            .show()
}