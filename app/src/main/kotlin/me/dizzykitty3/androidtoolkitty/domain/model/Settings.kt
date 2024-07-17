package me.dizzykitty3.androidtoolkitty.domain.model

import me.dizzykitty3.androidtoolkitty.BuildConfig

data class Settings(
    var switchToFadeAnimation: Boolean = false,
    var dynamicColor: Boolean = true,
    var devMode: Boolean = BuildConfig.DEBUG
)