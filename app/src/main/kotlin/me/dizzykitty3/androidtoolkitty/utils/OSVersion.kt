package me.dizzykitty3.androidtoolkitty.utils

import android.os.Build

@Suppress("unused", "MemberVisibilityCanBePrivate")
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
    fun api35(): Boolean = android15()

    /**
     * Upside Down Cake
     *
     * 1. Activity.overrideActivityTransition()
     * 2. TileService.startActivityAndCollapse(Intent) -> TileService.startActivityAndCollapse(PendingIntent)
     * 3. android.permission.DETECT_SCREEN_CAPTURE, Activity.ScreenCaptureCallback
     */
    fun android14(): Boolean = osVersion >= 34
    fun api34(): Boolean = android14()

    /**
     * Tiramisu
     *
     * 1. Photo picker
     * 2. Displays a standard visual confirmation when content is added to the clipboard
     */
    fun android13(): Boolean = osVersion >= 33
    fun api33(): Boolean = android13()

    /**
     * Sv2
     */
    fun android12L(): Boolean = osVersion >= 32
    fun api32(): Boolean = android12L()

    /**
     * S
     *
     * 1. Material You dynamic color
     * 2. Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
     * 3. Manifest.permission.BLUETOOTH_CONNECT
     * 4. BluetoothAdapter.getDefaultAdapter() -> BluetoothManager.getAdapter()
     */
    fun android12(): Boolean = osVersion >= 31
    fun api31(): Boolean = android12()

    /**
     * R
     */
    fun android11(): Boolean = osVersion >= 30
    fun api30(): Boolean = android11()

    /**
     * Q
     *
     * 1. Unless your app is the default input method editor (IME) or is the app that currently has focus, your app cannot access clipboard data.
     */
    fun android10(): Boolean = osVersion >= 29
    fun api29(): Boolean = android10()

    /**
     * Pie
     *
     * 1. ClipboardManager.clearPrimaryClip()
     */
    fun android9(): Boolean = osVersion >= 28
    fun api28(): Boolean = android9()

    /**
     * Oreo
     *
     * 1. Adaptive icons
     * 2. LocalTime.now()
     */
    fun android8(): Boolean = osVersion >= 26
    fun api26(): Boolean = android8()

    /**
     * Nougat
     */
    fun android7(): Boolean = osVersion >= 24
    fun api24(): Boolean = android7()

    /**
     * Marshmallow
     *
     * 1. View.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
     * 2. ConnectivityManager.getActiveNetwork()
     */
    fun android6(): Boolean = osVersion >= 23
    fun api23(): Boolean = android6()

    /**
     * Lollipop
     */
    fun android5(): Boolean = osVersion >= 21
    fun api21(): Boolean = android5()
}