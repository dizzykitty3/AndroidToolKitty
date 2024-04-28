package me.dizzykitty3.androidtoolkitty.data.room

interface AppContainer {
    val urlHistoryRepository: UrlHistoryRepository
}

class AppDataContainer : AppContainer {
    override val urlHistoryRepository: UrlHistoryRepository by lazy {
        OfflineUrlHistoryRepository(MainDatabase.getInstance().urlHistoryDao())
    }
}