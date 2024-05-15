package me.dizzykitty3.androidtoolkitty.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface URLHistoryDao {
    @Query("SELECT * FROM url_history ORDER BY id DESC")
    fun getAll(): Flow<List<URLHistory>>

    @Query("SELECT * from url_history WHERE id = :id")
    fun get(id: Int): Flow<URLHistory>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(urlHistory: URLHistory)

    @Delete
    suspend fun delete(urlHistory: URLHistory)
}