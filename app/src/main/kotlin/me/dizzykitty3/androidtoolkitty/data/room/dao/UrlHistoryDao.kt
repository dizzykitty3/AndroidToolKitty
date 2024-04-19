package me.dizzykitty3.androidtoolkitty.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.dizzykitty3.androidtoolkitty.data.room.entity.UrlHistory

@Dao
interface UrlHistoryDao {
    @Query("SELECT * FROM UrlHistory ORDER BY `key` DESC")
    fun getAll(): List<UrlHistory>

    @Insert
    fun insert(urlHistory: UrlHistory)

    @Delete
    fun delete(urlHistory: UrlHistory)

    @Delete
    fun deleteAll(urlHistories: UrlHistory)
}