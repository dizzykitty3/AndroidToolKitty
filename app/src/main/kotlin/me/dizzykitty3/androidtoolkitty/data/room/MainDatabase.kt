package me.dizzykitty3.androidtoolkitty.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

// Reference codelab: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room

@Database(
    entities = [UrlHistory::class],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun urlHistoryDao(): UrlHistoryDao

    companion object {
        private const val ROOM_DATABASE_NAME = "ToolKittyDatabase"

        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(): MainDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    app.applicationContext,
                    MainDatabase::class.java, ROOM_DATABASE_NAME
                ).build().also { INSTANCE = it }
            }
    }
}