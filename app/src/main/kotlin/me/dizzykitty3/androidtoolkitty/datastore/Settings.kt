package me.dizzykitty3.androidtoolkitty.datastore

data class Settings(
    // Note: Do not delete variables that are no longer in use, as this could cause
    // the app to crash on launch if upgraded from an older version.

    // Settings > Appearance
    val dynamicColor: Boolean = true,

    // Settings > General
    val autoClearClipboard: Boolean = false,
    val switchToBingSearch: Boolean = false,

    // Records
    val lastSelectedPlatformIndex: Int = 0
)