package me.dizzykitty3.androidtoolkitty.datastore

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

val LocalSettingsViewModel = staticCompositionLocalOf<SettingsViewModel> {
    error("No SettingsViewModel provided")
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val settingsState = repository.settingsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserSettings(true, false, false, 0)
    )

    fun toggleDynamicColor(enabled: Boolean) {
        viewModelScope.launch { repository.toggleDynamicColor(enabled) }
    }

    fun toggleAutoClearClipboard(enabled: Boolean) {
        viewModelScope.launch { repository.toggleAutoClearClipboard(enabled) }
    }

    fun toggleSwitchToBingSearch(enabled: Boolean) {
        viewModelScope.launch { repository.toggleSwitchToBingSearch(enabled) }
    }

    fun updateLastSelectedPlatformIndex(index: Int) {
        viewModelScope.launch { repository.updateLastSelectedPlatformIndex(index) }
    }
}
