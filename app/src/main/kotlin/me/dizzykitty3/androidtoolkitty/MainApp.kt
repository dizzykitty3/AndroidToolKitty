package me.dizzykitty3.androidtoolkitty

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import me.dizzykitty3.androidtoolkitty.data.room.AppContainer
import me.dizzykitty3.androidtoolkitty.data.room.AppDataContainer

@HiltAndroidApp
class MainApp : Application() {
    companion object {
        private const val TAG = "MainApp"
        lateinit var appContext: Context private set
        lateinit var container: AppContainer
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        appContext = applicationContext
        container = AppDataContainer()
    }
}