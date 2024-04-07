package me.dizzykitty3.androidtoolkitty.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    companion object {
        private const val TAG = "SettingsViewModel"
        private const val PREF_NAME = "Settings"
        private const val IS_AUTO_CLEAR_CLIPBOARD = "is_auto_clear_clipboard"
        private const val LAST_TIME_SELECTED_PLATFORM_INDEX = "last_time_selected_platform_index"
        private const val HAVE_OPENED_SETTINGS_SCREEN = "have_opened_settings_screen"
        private const val IS_ONE_HANDED_MODE = "is_one_handed_mode"
        private const val IS_DYNAMIC_COLOR = "is_dynamic_color"
        private const val CUSTOM_VOLUME = "custom_volume"
        private const val VOLUME_OPTION_LABEL = "volume_option_label"
        private const val SLIDER_INCREMENT_5_PERCENT = "slider_increment_5_percent"
    }

    private fun getSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getIsAutoClearClipboard(context: Context): Boolean {
        return getSharedPrefs(context).getBoolean(IS_AUTO_CLEAR_CLIPBOARD, false)
    }

    fun setIsAutoClearClipboard(context: Context, isAutoClear: Boolean) {
        Log.d(TAG, "is auto clear clipboard = $isAutoClear")
        with(getSharedPrefs(context).edit()) {
            putBoolean(IS_AUTO_CLEAR_CLIPBOARD, isAutoClear)
            apply()
        }
    }

    fun getCardShowedState(context: Context, cardId: String): Boolean {
        return getSharedPrefs(context).getBoolean(cardId, true)
    }

    fun saveCardShowedState(context: Context, cardId: String, isShowed: Boolean) {
        Log.d(TAG, "$cardId is showed = $isShowed")
        with(getSharedPrefs(context).edit()) {
            putBoolean(cardId, isShowed)
            apply()
        }
    }

    fun getLastTimeSelectedSocialPlatform(context: Context): Int {
        return getSharedPrefs(context).getInt(LAST_TIME_SELECTED_PLATFORM_INDEX, 0)
    }

    fun saveSelectedSocialPlatform(context: Context, lastTimePlatformIndex: Int) {
        Log.d(TAG, "last time platform index = $lastTimePlatformIndex")
        with(getSharedPrefs(context).edit()) {
            putInt(LAST_TIME_SELECTED_PLATFORM_INDEX, lastTimePlatformIndex)
            apply()
        }
    }

    fun getHaveOpenedSettingsScreen(context: Context): Boolean {
        return getSharedPrefs(context).getBoolean(HAVE_OPENED_SETTINGS_SCREEN, false)
    }

    fun setHaveOpenedSettingsScreen(context: Context, haveOpened: Boolean) {
        Log.d(TAG, "have opened settings menu = $haveOpened")
        with(getSharedPrefs(context).edit()) {
            putBoolean(HAVE_OPENED_SETTINGS_SCREEN, haveOpened)
            apply()
        }
    }

    fun getIsOneHandedMode(context: Context): Boolean {
        return getSharedPrefs(context).getBoolean(IS_ONE_HANDED_MODE, false)
    }

    fun setIsOneHandedMode(context: Context, isOneHandedMode: Boolean) {
        Log.d(TAG, "is single hand mode = $isOneHandedMode")
        with(getSharedPrefs(context).edit()) {
            putBoolean(IS_ONE_HANDED_MODE, isOneHandedMode)
            apply()
        }
    }

    fun getIsDynamicColor(context: Context): Boolean {
        return getSharedPrefs(context).getBoolean(IS_DYNAMIC_COLOR, true)
    }

    fun setIsDynamicColor(context: Context, isDynamicColor: Boolean) {
        Log.d(TAG, "is dynamic color = $isDynamicColor")
        with(getSharedPrefs(context).edit()) {
            putBoolean(IS_DYNAMIC_COLOR, isDynamicColor)
            apply()
        }
    }

    fun getIsSliderIncrementFivePercent(context: Context): Boolean {
        return getSharedPrefs(context).getBoolean(SLIDER_INCREMENT_5_PERCENT, false)
    }

    fun setIsSliderIncrementFivePercent(context: Context, incrementFivePercent: Boolean) {
        Log.d(TAG, "is slider increment 5% = $incrementFivePercent")
        with(getSharedPrefs(context).edit()) {
            putBoolean(SLIDER_INCREMENT_5_PERCENT, incrementFivePercent)
            apply()
        }
    }

    fun getCustomVolume(context: Context): Int {
        return getSharedPrefs(context).getInt(CUSTOM_VOLUME, Int.MIN_VALUE)
    }

    fun setCustomVolume(context: Context, customVolume: Int) {
        Log.d(TAG, "custom volume = $customVolume")
        with(getSharedPrefs(context).edit()) {
            putInt(CUSTOM_VOLUME, customVolume)
            apply()
        }
    }

    fun getCustomVolumeOptionLabel(context: Context): String? {
        return getSharedPrefs(context).getString(VOLUME_OPTION_LABEL, "")
    }

    fun setCustomVolumeOptionLabel(context: Context, customOptionLabel: String) {
        Log.d(TAG, "custom volume option label = $customOptionLabel")
        with(getSharedPrefs(context).edit()) {
            putString(VOLUME_OPTION_LABEL, customOptionLabel)
            apply()
        }
    }

    fun clear(context: Context) {
        Log.d(TAG, "erase all app data")
        with(getSharedPrefs(context).edit()) {
            clear()
            apply()
        }
    }
}