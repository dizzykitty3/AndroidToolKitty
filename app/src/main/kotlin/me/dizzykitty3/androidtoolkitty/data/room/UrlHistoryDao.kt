package me.dizzykitty3.androidtoolkitty.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UrlHistoryDao {
    @Query("SELECT * FROM url_history ORDER BY id DESC")
    fun getAll(): Flow<List<UrlHistory>>

    @Query("SELECT * from url_history WHERE id = :id")
    fun get(id: Int): Flow<UrlHistory>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(urlHistory: UrlHistory)

    @Delete
    suspend fun delete(urlHistory: UrlHistory)
}