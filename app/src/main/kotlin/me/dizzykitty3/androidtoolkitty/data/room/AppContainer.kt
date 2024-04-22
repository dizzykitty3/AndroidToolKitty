package me.dizzykitty3.androidtoolkitty.data.room

import android.content.Context

interface AppContainer {
    val urlHistoryRepository: UrlHistoryRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val urlHistoryRepository: UrlHistoryRepository by lazy {
        OfflineUrlHistoryRepository(MainDatabase.getInstance(context).urlHistoryDao())
    }
}