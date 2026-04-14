package me.dizzykitty3.androidtoolkitty.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object Keys {
        val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
        val AUTO_CLEAR_CLIPBOARD = booleanPreferencesKey("auto_clear_clipboard")
        val SWITCH_TO_BING_SEARCH = booleanPreferencesKey("switch_to_bing_search")
        val LAST_SELECTED_PLATFORM_INDEX = intPreferencesKey("last_selected_platform_index")
    }

    val settingsFlow: Flow<UserSettings> = dataStore.data.map { preferences ->
        UserSettings(
            dynamicColor = preferences[Keys.DYNAMIC_COLOR] ?: true,
            autoClearClipboard = preferences[Keys.AUTO_CLEAR_CLIPBOARD] ?: false,
            switchToBingSearch = preferences[Keys.SWITCH_TO_BING_SEARCH] ?: false,
            lastSelectedPlatformIndex = preferences[Keys.LAST_SELECTED_PLATFORM_INDEX] ?: 0,
        )
    }

    suspend fun toggleDynamicColor(enabled: Boolean) {
        dataStore.edit { it[Keys.DYNAMIC_COLOR] = enabled }
    }

    suspend fun toggleAutoClearClipboard(enabled: Boolean) {
        dataStore.edit { it[Keys.AUTO_CLEAR_CLIPBOARD] = enabled }
    }

    suspend fun toggleSwitchToBingSearch(enabled: Boolean) {
        dataStore.edit { it[Keys.SWITCH_TO_BING_SEARCH] = enabled }
    }

    suspend fun updateLastSelectedPlatformIndex(index: Int) {
        dataStore.edit { it[Keys.LAST_SELECTED_PLATFORM_INDEX] = index }
    }
}

data class UserSettings(
    val dynamicColor: Boolean,
    val autoClearClipboard: Boolean,
    val switchToBingSearch: Boolean,
    val lastSelectedPlatformIndex: Int,
)
