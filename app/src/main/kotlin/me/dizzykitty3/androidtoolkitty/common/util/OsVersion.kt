package me.dizzykitty3.androidtoolkitty.common.util

import android.os.Build

object OsVersion {
    private val osVersion = Build.VERSION.SDK_INT

    /**
     * Photo picker
     */
    @JvmStatic
    fun android13(): Boolean = osVersion >= 33

    /**
     * Material You dynamic color
     */
    @JvmStatic
    fun android12(): Boolean = osVersion >= 31

    /**
     * Adaptive icons
     */
    @JvmStatic
    fun android8(): Boolean = osVersion >= 26
}