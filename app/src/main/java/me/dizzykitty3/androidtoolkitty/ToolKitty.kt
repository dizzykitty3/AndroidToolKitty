package me.dizzykitty3.androidtoolkitty

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import timber.log.Timber

@HiltAndroidApp
class ToolKitty : Application() {
    companion object {
        lateinit var appContext: Context private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        if (BuildConfig.DEBUG || SettingsSharedPref.isLoggingEnabled) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("onCreate")
    }
}
