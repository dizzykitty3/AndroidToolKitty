package me.dizzykitty3.androidtoolkitty.domain.model

import me.dizzykitty3.androidtoolkitty.BuildConfig

private val dev = BuildConfig.DEBUG

data class Settings(
    // Settings > Appearance
    var dynamicColor: Boolean = true,
    var fadeAnimation: Boolean = false,

    // Settings > Debugging
    var devMode: Boolean = dev,
    var bottomAppBar: Boolean = dev,

    // Records
    var haveOpenedSettings: Boolean = false
)