package me.dizzykitty3.androidtoolkitty.data.room

import kotlinx.coroutines.flow.Flow

interface URLHistoryRepository {
    fun getAll(): Flow<List<URLHistory>>

    fun get(id: Int): Flow<URLHistory?>

    suspend fun insert(urlHistory: URLHistory)

    suspend fun delete(urlHistory: URLHistory)
}