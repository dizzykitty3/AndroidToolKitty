package me.dizzykitty3.androidtoolkitty.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.dizzykitty3.androidtoolkitty.data.room.dao.UrlHistoryDao
import me.dizzykitty3.androidtoolkitty.data.room.entity.UrlHistory

@Database(
    entities = [UrlHistory::class],
    version = 1
)
abstract class ToolKittyDatabase : RoomDatabase() {
    abstract fun urlHistoryDao(): UrlHistoryDao
}