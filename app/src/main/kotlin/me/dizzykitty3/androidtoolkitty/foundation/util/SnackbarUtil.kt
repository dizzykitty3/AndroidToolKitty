package me.dizzykitty3.androidtoolkitty.foundation.util

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

@Suppress("SpellCheckingInspection")
class SnackbarUtil(private val view: View) {
    fun snackbar(message: String) =
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

    fun snackbar(@StringRes message: Int) =
        Snackbar.make(view, app.applicationContext.getString(message), Snackbar.LENGTH_SHORT).show()

    fun snackbar(
        @StringRes message: Int,
        @StringRes buttonText: Int,
        buttonColor: Int,
        buttonClickListener: View.OnClickListener
    ) =
        Snackbar.make(view, app.applicationContext.getString(message), Snackbar.LENGTH_LONG)
            .setAction(app.applicationContext.getString(buttonText), buttonClickListener)
            .setActionTextColor(buttonColor)
            .show()
}