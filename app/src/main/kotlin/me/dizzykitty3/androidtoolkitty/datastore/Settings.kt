package me.dizzykitty3.androidtoolkitty.datastore

data class Settings(
    // Settings > Appearance
    var dynamicColor: Boolean = true,
    var forceDarkMode: Boolean = false,

    // Settings > General
    var autoClearClipboard: Boolean = false,

    // Settings > Debugging
    var devMode: Boolean = false,

    // Records
    var haveOpenedSettings: Boolean = false
)