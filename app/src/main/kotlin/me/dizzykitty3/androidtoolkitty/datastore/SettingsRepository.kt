package me.dizzykitty3.androidtoolkitty.datastore

interface SettingsRepository {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun putInt(key: String, value: Int)
    suspend fun getInt(key: String): Int?
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Boolean?
}