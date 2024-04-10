package me.dizzykitty3.androidtoolkitty.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.dizzykitty3.androidtoolkitty.data.room.dao.UrlHistoryDao
import me.dizzykitty3.androidtoolkitty.data.room.entity.UrlHistory

@Database(
    entities = [UrlHistory::class],
    version = 1
)
abstract class ToolKittyDatabase : RoomDatabase() {
    abstract fun urlHistoryDao(): UrlHistoryDao

    companion object {
        private const val ROOM_DATABASE_NAME = "ToolKittyDatabase"

        @Volatile
        private var INSTANCE: ToolKittyDatabase? = null

        fun getInstance(context: Context): ToolKittyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ToolKittyDatabase::class.java, ROOM_DATABASE_NAME
                ).build().also { INSTANCE = it }
            }
    }
}