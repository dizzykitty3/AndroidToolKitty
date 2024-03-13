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

    fun getHaveOpenedSettingsScreen(): Boolean {
        return sharedPrefs.getBoolean(HAVE_OPENED_SETTINGS_SCREEN, false)
    }

    fun setHaveOpenedSettingsScreen(haveOpened: Boolean) {
        Actions.debugLog("have opened settings menu = $haveOpened")
        with(sharedPrefs.edit()) {
            putBoolean(HAVE_OPENED_SETTINGS_SCREEN, haveOpened)
            apply()
        }
    }

    fun getIsSingleHandMode(): Boolean {
        return sharedPrefs.getBoolean(IS_SINGLE_HAND_MODE, false)
    }

    fun setIsSingleHandMode(isSingleHandMode: Boolean) {
        Actions.debugLog("is single hand mode = $isSingleHandMode")
        with(sharedPrefs.edit()) {
            putBoolean(IS_SINGLE_HAND_MODE, isSingleHandMode)
            apply()
        }
    }

    companion object {
        private const val PREF_NAME = "Settings"
        private const val IS_AUTO_CLEAR_CLIPBOARD = "is_auto_clear_clipboard"
        private const val LAST_TIME_SELECTED_PLATFORM_INDEX = "last_time_selected_platform_index"
        private const val HAVE_OPENED_SETTINGS_SCREEN = "have_opened_settings_screen"
        private const val IS_SINGLE_HAND_MODE = "is_single_hand_mode"
    }
}