package me.dizzykitty3.androidtoolkitty.domain.utils

import android.annotation.SuppressLint
import android.view.HapticFeedbackConstants
import android.view.View

object HapticUtil {
    @SuppressLint("InlinedApi")
    fun View.hapticFeedback() =
        this.performHapticFeedback(
            if (OSVersion.api23()) HapticFeedbackConstants.CONTEXT_CLICK // requires API 23
            else HapticFeedbackConstants.KEYBOARD_TAP
        )
}