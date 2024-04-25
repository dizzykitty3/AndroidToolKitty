package me.dizzykitty3.androidtoolkitty.foundation.util

import android.os.Build

@Suppress("unused")
object OsVersion {
    private val osVersion = Build.VERSION.SDK_INT

    /**
     * 1. Photo picker
     */
    @JvmStatic
    fun android13(): Boolean = osVersion >= 33

    /**
     * 1. Material You dynamic color
     * 2. Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
     * 3. Manifest.permission.BLUETOOTH_CONNECT
     */
    @JvmStatic
    fun android12(): Boolean = osVersion >= 31

    /**
     * 1. Adaptive icons
     */
    @JvmStatic
    fun android8(): Boolean = osVersion >= 26
}