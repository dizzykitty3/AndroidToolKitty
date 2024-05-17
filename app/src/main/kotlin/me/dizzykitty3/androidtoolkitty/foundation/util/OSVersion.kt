package me.dizzykitty3.androidtoolkitty.foundation.util

import android.os.Build

@Suppress("unused")
object OSVersion {
    private val osVersion = Build.VERSION.SDK_INT

    /**
     * Vanilla Ice Cream
     *
     * [developer-preview-1](https://android-developers.googleblog.com/2024/02/first-developer-preview-android15.html)
     * [developer-preview-2](https://android-developers.googleblog.com/2024/03/the-second-developer-preview-of-android-15.html)
     * [beta-1](https://android-developers.googleblog.com/2024/04/the-first-beta-of-android-15.html)
     * [beta-2](https://android-developers.googleblog.com/2024/05/the-second-beta-of-android-15.html)
     */
    fun android15(): Boolean = osVersion >= 35

    /**
     * Upside Down Cake
     *
     * 1. Activity.overrideActivityTransition()
     * 2. TileService.startActivityAndCollapse(Intent) -> TileService.startActivityAndCollapse(PendingIntent)
     */
    fun android14(): Boolean = osVersion >= 34

    /**
     * Tiramisu
     *
     * 1. Photo picker
     * 2. Displays a standard visual confirmation when content is added to the clipboard
     */
    fun android13(): Boolean = osVersion >= 33

    /**
     * Sv2
     */
    fun android12L(): Boolean = osVersion >= 32

    /**
     * S
     *
     * 1. Material You dynamic color
     * 2. Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
     * 3. Manifest.permission.BLUETOOTH_CONNECT
     */
    fun android12(): Boolean = osVersion >= 31

    /**
     * R
     */
    fun android11(): Boolean = osVersion >= 30

    /**
     * Q
     *
     * 1. Unless your app is the default input method editor (IME)
     * or is the app that currently has focus,
     * your app cannot access clipboard data.
     */
    fun android10(): Boolean = osVersion >= 29

    /**
     * Pie
     *
     * 1. ClipboardManager.clearPrimaryClip()
     */
    fun android9(): Boolean = osVersion >= 28

    /**
     * Oreo
     *
     * 1. Adaptive icons
     */
    fun android8(): Boolean = osVersion >= 26
}