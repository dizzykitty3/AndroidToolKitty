package me.dizzykitty3.androidtoolkitty

import android.app.Application
import android.util.Log
import androidx.room.Room
import me.dizzykitty3.androidtoolkitty.data.room.database.ToolKittyDatabase
import me.dizzykitty3.androidtoolkitty.data.room.entity.UrlHistory

class ToolKittyApp : Application() {
    companion object {
        private const val TAG = "ToolKittyApp"
        private const val ROOM_DATABASE_NAME = "ToolKittyDatabase"
        lateinit var app: ToolKittyApp private set
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        app = this

        val db = Room.databaseBuilder(
            applicationContext,
            ToolKittyDatabase::class.java, ROOM_DATABASE_NAME
        ).build()

        val urlHistoryDao = db.urlHistoryDao()
        val urlHistories: List<UrlHistory> = urlHistoryDao.getAll()
    }
}