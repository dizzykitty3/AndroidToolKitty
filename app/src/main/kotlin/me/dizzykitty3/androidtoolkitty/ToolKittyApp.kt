package me.dizzykitty3.androidtoolkitty

import android.app.Application

class ToolKittyApp : Application() {
    companion object {
        lateinit var app: ToolKittyApp private set
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}