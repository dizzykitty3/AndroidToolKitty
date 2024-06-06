package me.dizzykitty3.androidtoolkitty.app_components

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.data.room.AppContainer
import me.dizzykitty3.androidtoolkitty.data.room.AppDataContainer
import timber.log.Timber

@HiltAndroidApp
class MainApp : Application() {
    companion object {
        lateinit var appContext: Context private set
        lateinit var container: AppContainer
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        Timber.d("onCreate")
        appContext = applicationContext
        container = AppDataContainer()
    }
}