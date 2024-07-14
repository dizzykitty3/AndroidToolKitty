package me.dizzykitty3.androidtoolkitty.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.dizzykitty3.androidtoolkitty.domain.model.Settings
import me.dizzykitty3.androidtoolkitty.domain.usecases.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val settingsUseCase: SettingsUseCase
) : ViewModel() {
    private val _settings = mutableStateOf(Settings())
    var settings: State<Settings> = _settings

    init {
        viewModelScope.launch {
            loadSettings()
        }
    }

    private suspend fun loadSettings() {
        val loadedSettings = runBlocking(Dispatchers.IO) {
            settingsUseCase.loadSettingsFromRepository()
        }
        _settings.value = loadedSettings
    }

    fun update(newSettings: Settings) {
        _settings.value = newSettings.copy()
        viewModelScope.launch {
            settingsUseCase.saveSettingsToRepository(newSettings)
        }
    }
}