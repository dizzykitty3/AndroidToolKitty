package me.dizzykitty3.androidtoolkitty.datastore

data class Settings(
    // Settings > Appearance
    var dynamicColor: Boolean = true,
    var forceDarkMode: Boolean = false,
    var dismissLangTip: Boolean = false,
    var hideGreetings: Boolean = false,

    // Settings > General
    var autoClearClipboard: Boolean = false,

    // Records
    var haveOpenedSettings: Boolean = false,
    var lastSelectedPlatformIndex: Int = 0
)