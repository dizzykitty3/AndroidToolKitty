package me.dizzykitty3.androidtoolkitty.utils

import android.os.Build

@Suppress("unused", "MemberVisibilityCanBePrivate")
object OSVersion {
    private val osVersion = Build.VERSION.SDK_INT

    /**
     * [developer-preview-1](https://android-developers.googleblog.com/2024/02/first-developer-preview-android15.html)
     * [developer-preview-2](https://android-developers.googleblog.com/2024/03/the-second-developer-preview-of-android-15.html)
     * [beta-1](https://android-developers.googleblog.com/2024/04/the-first-beta-of-android-15.html)
     * [beta-2](https://android-developers.googleblog.com/2024/05/the-second-beta-of-android-15.html)
     */
    fun a15(): Boolean = osVersion >= 35
    fun api35(): Boolean = a15()
    fun vanillaIceCream(): Boolean = a15()
    fun v(): Boolean = a15()

    /**
     * 1. Activity.overrideActivityTransition()
     * 2. TileService.startActivityAndCollapse(Intent) -> TileService.startActivityAndCollapse(PendingIntent)
     * 3. android.permission.DETECT_SCREEN_CAPTURE, Activity.ScreenCaptureCallback
     */
    fun a14(): Boolean = osVersion >= 34
    fun api34(): Boolean = a14()
    fun upsideDownCake(): Boolean = a14()
    fun u(): Boolean = a14()

    /**
     * 1. Photo picker
     * 2. Displays a standard visual confirmation when content is added to the clipboard
     */
    fun a13(): Boolean = osVersion >= 33
    fun api33(): Boolean = a13()
    fun tiramisu(): Boolean = a13()
    fun t(): Boolean = a13()

    fun a12L(): Boolean = osVersion >= 32
    fun api32(): Boolean = a12L()
    fun sV2(): Boolean = a12L()

    /**
     * 1. Material You dynamic color
     * 2. Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
     * 3. Manifest.permission.BLUETOOTH_CONNECT
     * 4. BluetoothAdapter.getDefaultAdapter() -> BluetoothManager.getAdapter()
     * 5. Cannot use ACCESS_FINE_LOCATION without ACCESS_COARSE_LOCATION
     */
    fun a12(): Boolean = osVersion >= 31
    fun api31(): Boolean = a12()
    fun s(): Boolean = a12()

    fun a11(): Boolean = osVersion >= 30
    fun api30(): Boolean = a11()
    fun r(): Boolean = a11()

    /**
     * 1. Unless your app is the default input method editor (IME) or is the app that currently has focus, your app cannot access clipboard data.
     */
    fun a10(): Boolean = osVersion >= 29
    fun api29(): Boolean = a10()
    fun q(): Boolean = a10()

    /**
     * 1. ClipboardManager.clearPrimaryClip()
     */
    fun a9(): Boolean = osVersion >= 28
    fun api28(): Boolean = a9()
    fun pie(): Boolean = a9()
    fun p(): Boolean = a9()

    /**
     * 1. Adaptive icons
     * 2. LocalTime.now()
     */
    fun a8(): Boolean = osVersion >= 26
    fun api26(): Boolean = a8()
    fun oreo(): Boolean = a8()
    fun o(): Boolean = a8()

    /**
     * 1. Create a TileService
     * 2. Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
     */
    fun a7(): Boolean = osVersion >= 24
    fun api24(): Boolean = a7()
    fun nougat(): Boolean = a7()
    fun n(): Boolean = a7()

    /**
     * 1. View.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
     * 2. ConnectivityManager.getActiveNetwork()
     * 3. Icon.createWithResource()
     * 4. Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
     * 5. Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
     * 6. Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
     */
    fun a6(): Boolean = osVersion >= 23
    fun api23(): Boolean = a6()
    fun marshmallow(): Boolean = a6()
    fun m(): Boolean = a6()

    fun a5(): Boolean = osVersion >= 21
    fun api21(): Boolean = a5()
    fun lollipop(): Boolean = a5()
    fun l(): Boolean = a5()
}