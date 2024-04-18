package me.dizzykitty3.androidtoolkitty.foundation.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackbarUtil(private val view: View) {
    fun snackbar(message: String) =
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

    fun snackbar(
        message: String,
        buttonText: String,
        buttonColor: Int,
        buttonClickListener: View.OnClickListener
    ) =
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(buttonText, buttonClickListener)
            .setActionTextColor(buttonColor)
            .show()
}