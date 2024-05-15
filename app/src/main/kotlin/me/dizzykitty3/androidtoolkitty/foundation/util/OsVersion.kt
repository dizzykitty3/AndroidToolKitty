package me.dizzykitty3.androidtoolkitty.foundation.util

import android.os.Build

object OsVersion {
    private val osVersion = Build.VERSION.SDK_INT

    /**
     * 1. Activity.overrideActivityTransition()
     */
    fun android14(): Boolean = osVersion >= 34 // Upside Down Cake

    /**
     * 1. Photo picker
     * 2. Displays a standard visual confirmation when content is added to the clipboard
     */
    fun android13(): Boolean = osVersion >= 33 // Tiramisu

    /**
     * 1. Material You dynamic color
     * 2. Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
     * 3. Manifest.permission.BLUETOOTH_CONNECT
     */
    fun android12(): Boolean = osVersion >= 31 // S

    /**
     * 1. ClipboardManager.clearPrimaryClip()
     */
    fun android9(): Boolean = osVersion >= 28 // Pie

    /**
     * 1. Adaptive icons
     */
    fun android8(): Boolean = osVersion >= 26 // Oreo
}