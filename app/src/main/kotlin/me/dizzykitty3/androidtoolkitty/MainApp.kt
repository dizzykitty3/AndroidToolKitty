package me.dizzykitty3.androidtoolkitty

import android.app.Application
import android.util.Log

class MainApp : Application() {
    companion object {
        private const val TAG = "ToolKittyApp"
        lateinit var app: MainApp private set
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        app = this
    }
}