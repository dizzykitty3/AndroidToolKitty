package me.dizzykitty3.androidtoolkitty.foundation.util

import android.os.Build

@Suppress("unused")
object OsVersion {
    private val osVersion = Build.VERSION.SDK_INT

    /**
     * Android V, Vanilla Ice Cream
     */
    fun android15(): Boolean = osVersion >= 35

    /**
     * Android U, Upside Down Cake
     * 1. Activity.overrideActivityTransition()
     */
    fun android14(): Boolean = osVersion >= 34

    /**
     * Android T, Tiramisu
     * 1. Photo picker
     * 2. Displays a standard visual confirmation when content is added to the clipboard
     */
    fun android13(): Boolean = osVersion >= 33

    /**
     * Android Sv2
     */
    fun android12L(): Boolean = osVersion >= 32

    /**
     * Android S
     * 1. Material You dynamic color
     * 2. Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
     * 3. Manifest.permission.BLUETOOTH_CONNECT
     */
    fun android12(): Boolean = osVersion >= 31

    /**
     * Android R
     */
    fun android11(): Boolean = osVersion >= 30

    /**
     * Android Q
     * 1. Unless your app is the default input method editor (IME)
     * or is the app that currently has focus,
     * your app cannot access clipboard data.
     */
    fun android10(): Boolean = osVersion >= 29

    /**
     * Android P, Pie
     * 1. ClipboardManager.clearPrimaryClip()
     */
    fun android9(): Boolean = osVersion >= 28

    /**
     * Android O, Oreo
     * 1. Adaptive icons
     */
    fun android8(): Boolean = osVersion >= 26
}