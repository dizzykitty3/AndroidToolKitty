package me.dizzykitty3.androidtoolkitty.datastore

data class Settings(
    // Settings > Appearance
    var dynamicColor: Boolean = true,
    var forceDarkMode: Boolean = false,
    var hideGreetings: Boolean = false,
    var customFont: Boolean = false,
    var customAnimation: Boolean = false,

    // Settings > General
    var autoClearClipboard: Boolean = false,

    // Records
    var haveOpenedSettings: Boolean = false,
    var lastSelectedPlatformIndex: Int = 0
)