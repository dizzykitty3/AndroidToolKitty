package me.dizzykitty3.androidtoolkitty.data.room

import kotlinx.coroutines.flow.Flow

class OfflineUrlHistoryRepository(
    private val urlHistoryDao: UrlHistoryDao
) : UrlHistoryRepository {
    override fun getAll(): Flow<List<UrlHistory>> =
        urlHistoryDao.getAll()

    override fun get(id: Int): Flow<UrlHistory?> =
        urlHistoryDao.get(id)

    override suspend fun insert(urlHistory: UrlHistory) =
        urlHistoryDao.insert(urlHistory)

    override suspend fun delete(urlHistory: UrlHistory) =
        urlHistoryDao.delete(urlHistory)
}