package me.dizzykitty3.androidtoolkitty.utils

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext

object SnackbarUtil {
    fun show(view: View, message: String) =
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

    fun show(view: View, @StringRes message: Int) =
        Snackbar.make(view, appContext.getString(message), Snackbar.LENGTH_SHORT).show()

    fun show(
        view: View,
        @StringRes message: Int,
        @StringRes buttonText: Int,
        textColor: Int,
        buttonColor: Int,
        buttonClickListener: View.OnClickListener
    ) =
        Snackbar.make(view, appContext.getString(message), Snackbar.LENGTH_LONG)
            .setTextColor(textColor)
            .setAction(appContext.getString(buttonText), buttonClickListener)
            .setActionTextColor(buttonColor)
            .show()
}