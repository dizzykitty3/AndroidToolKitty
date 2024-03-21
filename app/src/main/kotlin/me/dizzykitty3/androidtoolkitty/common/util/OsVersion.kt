package me.dizzykitty3.androidtoolkitty.common.util

import android.os.Build

@Suppress("unused")
object OsVersion {
    private val osVersion = Build.VERSION.SDK_INT

    /**
     * Photo picker
     */
    @JvmStatic
    fun android13(): Boolean = osVersion >= 33

    /**
     * Material You dynamic color
     * Intent(Settings.ACTION_AUTO_ROTATE_SETTINGS)
     */
    @JvmStatic
    fun android12(): Boolean = osVersion >= 31

    /**
     * Adaptive icons
     */
    @JvmStatic
    fun android8(): Boolean = osVersion >= 26
}