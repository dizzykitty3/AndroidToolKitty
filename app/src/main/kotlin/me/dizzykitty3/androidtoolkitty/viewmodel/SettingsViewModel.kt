package me.dizzykitty3.androidtoolkitty.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import me.dizzykitty3.androidtoolkitty.Actions

class SettingsViewModel(context: Context) : ViewModel() {
    private val sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getIsAutoClearClipboard(): Boolean {
        return sharedPrefs.getBoolean(IS_AUTO_CLEAR_CLIPBOARD, false)
    }

    fun setIsAutoClearClipboard(isAutoClear: Boolean) {
        Actions.debugLog("is auto clear clipboard = $isAutoClear")
        with(sharedPrefs.edit())
        {
            putBoolean(IS_AUTO_CLEAR_CLIPBOARD, isAutoClear)
            apply()
        }
    }

    fun getCardExpandedState(cardId: String): Boolean {
        return sharedPrefs.getBoolean(cardId, true)
    }

    fun saveCardExpandedState(cardId: String, isExpanded: Boolean) {
        Actions.debugLog("$cardId is expanded = $isExpanded")
        with(sharedPrefs.edit()) {
            putBoolean(cardId, isExpanded)
            apply()
        }
    }

    fun getLastTimeSelectedSocialPlatform(): Int {
        return sharedPrefs.getInt(LAST_TIME_SELECTED_PLATFORM_INDEX, 0)
    }

    fun saveSelectedSocialPlatform(lastTimePlatformIndex: Int) {
        Actions.debugLog("last time platform index = $lastTimePlatformIndex")
        with(sharedPrefs.edit()) {
            putInt(LAST_TIME_SELECTED_PLATFORM_INDEX, lastTimePlatformIndex)
            apply()
        }
    }

    companion object {
        private const val PREF_NAME = "Settings"
        private const val IS_AUTO_CLEAR_CLIPBOARD = "isAutoClearClipboard"
        private const val LAST_TIME_SELECTED_PLATFORM_INDEX = "lastTimeSelectedPlatformIndex"
    }
}