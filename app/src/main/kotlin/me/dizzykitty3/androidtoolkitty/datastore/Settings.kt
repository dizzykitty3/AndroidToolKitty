package me.dizzykitty3.androidtoolkitty.datastore

data class Settings(
    // Note: Do not delete variables that are no longer in use, as this could cause
    // the app to crash on launch if upgraded from an older version.

    // Settings > Appearance
    var dynamicColor: Boolean = true,
    var forceDarkMode: Boolean = false, // removed
    var hideGreetings: Boolean = false, // removed
    var customFont: Boolean = false, // removed
    var customAnimation: Boolean = false, // removed

    // Settings > General
    var autoClearClipboard: Boolean = false,

    // Records
    var haveOpenedSettings: Boolean = false,
    var lastSelectedPlatformIndex: Int = 0
)