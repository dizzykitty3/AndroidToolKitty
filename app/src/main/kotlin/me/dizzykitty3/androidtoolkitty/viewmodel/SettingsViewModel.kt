package me.dizzykitty3.androidtoolkitty.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils.debugLog

class SettingsViewModel : ViewModel() {
    private fun getSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getIsAutoClearClipboard(context: Context): Boolean {
        return getSharedPrefs(context).getBoolean(IS_AUTO_CLEAR_CLIPBOARD, false)
    }

    fun setIsAutoClearClipboard(context: Context, isAutoClear: Boolean) {
        debugLog("is auto clear clipboard = $isAutoClear")
        with(getSharedPrefs(context).edit())
        {
            putBoolean(IS_AUTO_CLEAR_CLIPBOARD, isAutoClear)
            apply()
        }
    }

    fun getCardShowedState(context: Context, cardId: String): Boolean {
        return getSharedPrefs(context).getBoolean(cardId, true)
    }

    fun saveCardShowedState(context: Context, cardId: String, isShowed: Boolean) {
        debugLog("$cardId is showed = $isShowed")
        with(getSharedPrefs(context).edit()) {
            putBoolean(cardId, isShowed)
            apply()
        }
    }

    fun getLastTimeSelectedSocialPlatform(context: Context): Int {
        return getSharedPrefs(context).getInt(LAST_TIME_SELECTED_PLATFORM_INDEX, 0)
    }

    fun saveSelectedSocialPlatform(context: Context, lastTimePlatformIndex: Int) {
        debugLog("last time platform index = $lastTimePlatformIndex")
        with(getSharedPrefs(context).edit()) {
            putInt(LAST_TIME_SELECTED_PLATFORM_INDEX, lastTimePlatformIndex)
            apply()
        }
    }

    fun getHaveOpenedSettingsScreen(context: Context): Boolean {
        return getSharedPrefs(context).getBoolean(HAVE_OPENED_SETTINGS_SCREEN, false)
    }

    fun setHaveOpenedSettingsScreen(context: Context, haveOpened: Boolean) {
        debugLog("have opened settings menu = $haveOpened")
        with(getSharedPrefs(context).edit()) {
            putBoolean(HAVE_OPENED_SETTINGS_SCREEN, haveOpened)
            apply()
        }
    }

    fun getIsOneHandedMode(context: Context): Boolean {
        return getSharedPrefs(context).getBoolean(IS_ONE_HANDED_MODE, false)
    }

    fun setIsOneHandedMode(context: Context, isOneHandedMode: Boolean) {
        debugLog("is single hand mode = $isOneHandedMode")
        with(getSharedPrefs(context).edit()) {
            putBoolean(IS_ONE_HANDED_MODE, isOneHandedMode)
            apply()
        }
    }

    fun clear(context: Context) {
        debugLog("erase all app data")
        with(getSharedPrefs(context).edit()) {
            clear()
            apply()
        }
    }

    companion object {
        private const val PREF_NAME = "Settings"
        private const val IS_AUTO_CLEAR_CLIPBOARD = "is_auto_clear_clipboard"
        private const val LAST_TIME_SELECTED_PLATFORM_INDEX = "last_time_selected_platform_index"
        private const val HAVE_OPENED_SETTINGS_SCREEN = "have_opened_settings_screen"
        private const val IS_ONE_HANDED_MODE = "is_one_handed_mode"
    }
}