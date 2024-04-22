package me.dizzykitty3.androidtoolkitty.data.room

import kotlinx.coroutines.flow.Flow

interface UrlHistoryRepository {
    fun getAll(): Flow<List<UrlHistory>>

    fun get(id: Int): Flow<UrlHistory?>

    suspend fun insert(urlHistory: UrlHistory)

    suspend fun delete(urlHistory: UrlHistory)
}