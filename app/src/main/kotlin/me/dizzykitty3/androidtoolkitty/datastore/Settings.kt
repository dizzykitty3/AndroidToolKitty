package me.dizzykitty3.androidtoolkitty.datastore

import me.dizzykitty3.androidtoolkitty.BuildConfig

private val dev = BuildConfig.DEBUG

data class Settings(
    // Settings > Appearance
    var dynamicColor: Boolean = true,
    var forceDarkMode: Boolean = false,

    // Settings > General
    var autoClearClipboard: Boolean = false,

    // Settings > Debugging
    var devMode: Boolean = dev,
    var bottomAppBar: Boolean = dev,

    // Records
    var haveOpenedSettings: Boolean = false
)