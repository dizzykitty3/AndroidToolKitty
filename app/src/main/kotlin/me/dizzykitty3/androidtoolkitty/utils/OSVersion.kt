package me.dizzykitty3.androidtoolkitty.utils

import android.os.Build

/**
 * [Android Developers Blog](https://android-developers.googleblog.com/)
 */
@Suppress("unused")
object OSVersion {
    private val osVersion = Build.VERSION.SDK_INT

    /**
     * 35
     * [developer-preview-1](https://android-developers.googleblog.com/2024/02/first-developer-preview-android15.html)
     * [developer-preview-2](https://android-developers.googleblog.com/2024/03/the-second-developer-preview-of-android-15.html)
     * [beta-1](https://android-developers.googleblog.com/2024/04/the-first-beta-of-android-15.html)
     * [beta-2](https://android-developers.googleblog.com/2024/05/the-second-beta-of-android-15.html)
     * [beta-3](https://android-developers.googleblog.com/2024/06/the-third-beta-of-android-15.html)
     * [beta-4](https://android-developers.googleblog.com/2024/07/the-fourth-beta-of-android-15.html)
     * [AOSP release](https://android-developers.googleblog.com/2024/09/android-15-is-released-to-aosp.html)
     */
    fun android15(): Boolean = osVersion >= Build.VERSION_CODES.VANILLA_ICE_CREAM

    /**
     * 34
     * 1. Activity.overrideActivityTransition()
     * 2. TileService.startActivityAndCollapse(Intent) -> TileService.startActivityAndCollapse(PendingIntent)
     * 3. android.permission.DETECT_SCREEN_CAPTURE, Activity.ScreenCaptureCallback
     */
    fun android14(): Boolean = osVersion >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE

    /**
     * 33
     * 1. Photo picker
     * 2. Displays a standard visual confirmation when content is added to the clipboard
     * 3. Per-app language preferences
     */
    fun android13(): Boolean = osVersion >= Build.VERSION_CODES.TIRAMISU

    /**
     * 32
     */
    fun android12L(): Boolean = osVersion >= Build.VERSION_CODES.S_V2

    /**
     * 31
     * 1. Material You dynamic color
     * 2. Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
     * 3. Manifest.permission.BLUETOOTH_CONNECT
     * 4. BluetoothAdapter.getDefaultAdapter() -> BluetoothManager.getAdapter()
     * 5. Cannot use ACCESS_FINE_LOCATION without ACCESS_COARSE_LOCATION
     */
    fun android12(): Boolean = osVersion >= Build.VERSION_CODES.S

    /**
     * 30
     */
    fun android11(): Boolean = osVersion >= Build.VERSION_CODES.R

    /**
     * 29
     * 1. Unless your app is the default input method editor (IME) or is the app that currently has focus, your app cannot access clipboard data.
     */
    fun android10(): Boolean = osVersion >= Build.VERSION_CODES.Q

    /**
     * 28
     * 1. ClipboardManager.clearPrimaryClip()
     * 2. PackageInfo.getLongVersionCode()
     * 3. Build.SERIAL -> request READ_PHONE_STATE, then Build.getSerial()
     */
    fun android9(): Boolean = osVersion >= Build.VERSION_CODES.P

    /**
     * 26
     * 1. Adaptive icons
     * 2. LocalTime.now()
     */
    fun android8(): Boolean = osVersion >= Build.VERSION_CODES.O

    /**
     * 24
     * 1. Create a TileService
     * 2. Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
     */
    fun android7(): Boolean = osVersion >= Build.VERSION_CODES.N

    /**
     * 23
     * 1. View.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
     * 2. ConnectivityManager.getActiveNetwork()
     * 3. Icon.createWithResource()
     * 4. Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
     * 5. Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
     * 6. Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
     */
    fun android6(): Boolean = osVersion >= Build.VERSION_CODES.M

    /**
     * 21
     * 1. Jetpack Compose support
     */
    fun android5(): Boolean = osVersion >= Build.VERSION_CODES.LOLLIPOP
}