package me.dizzykitty3.androidtoolkitty.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext

// Reference codelab: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room

@Database(
    entities = [URLHistory::class],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun urlHistoryDao(): URLHistoryDao

    companion object {
        private const val ROOM_DATABASE_NAME = "ToolKittyDatabase"

        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(): MainDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    appContext,
                    MainDatabase::class.java, ROOM_DATABASE_NAME
                ).build().also { INSTANCE = it }
            }
    }
}