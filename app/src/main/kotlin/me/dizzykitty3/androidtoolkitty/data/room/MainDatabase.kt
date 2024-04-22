package me.dizzykitty3.androidtoolkitty.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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

        fun getInstance(context: Context): MainDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java, ROOM_DATABASE_NAME
                ).build().also { INSTANCE = it }
            }
    }
}