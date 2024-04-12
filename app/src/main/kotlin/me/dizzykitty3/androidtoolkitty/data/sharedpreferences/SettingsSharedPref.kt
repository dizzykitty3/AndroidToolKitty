package me.dizzykitty3.androidtoolkitty.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import me.dizzykitty3.androidtoolkitty.ToolKittyApp.Companion.app

object SettingsSharedPref {
    private const val TAG = "SettingsSharedPref"
    private const val PREF_NAME = "Settings"
    private const val IS_AUTO_CLEAR_CLIPBOARD = "is_auto_clear_clipboard"
    private const val LAST_TIME_SELECTED_PLATFORM_INDEX = "last_time_selected_platform_index"
    private const val HAVE_OPENED_SETTINGS_SCREEN = "have_opened_settings_screen"
    private const val IS_ONE_HANDED_MODE = "is_one_handed_mode"
    private const val IS_DYNAMIC_COLOR = "is_dynamic_color"
    private const val CUSTOM_VOLUME = "custom_volume"
    private const val VOLUME_OPTION_LABEL = "volume_option_label"
    private const val SLIDER_INCREMENT_5_PERCENT = "slider_increment_5_percent"
    private const val LUCKY_SPINNING_WHEEL_ITEMS = "lucky_spinning_wheel_items"

    private fun getSharedPrefs(): SharedPreferences {
        return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getIsAutoClearClipboard(): Boolean {
        return getSharedPrefs().getBoolean(IS_AUTO_CLEAR_CLIPBOARD, false)
    }

    fun setIsAutoClearClipboard(isAutoClear: Boolean) {
        Log.d(TAG, "is auto clear clipboard = $isAutoClear")
        with(getSharedPrefs().edit()) {
            putBoolean(IS_AUTO_CLEAR_CLIPBOARD, isAutoClear)
            apply()
        }
    }

    fun getCardShowedState(cardId: String): Boolean {
        return getSharedPrefs().getBoolean(cardId, true)
    }

    fun saveCardShowedState(cardId: String, isShowed: Boolean) {
        Log.d(TAG, "$cardId is showed = $isShowed")
        with(getSharedPrefs().edit()) {
            putBoolean(cardId, isShowed)
            apply()
        }
    }

    fun getLastTimeSelectedSocialPlatform(): Int {
        return getSharedPrefs().getInt(LAST_TIME_SELECTED_PLATFORM_INDEX, 0)
    }

    fun saveSelectedSocialPlatform(lastTimePlatformIndex: Int) {
        Log.d(TAG, "last time platform index = $lastTimePlatformIndex")
        with(getSharedPrefs().edit()) {
            putInt(LAST_TIME_SELECTED_PLATFORM_INDEX, lastTimePlatformIndex)
            apply()
        }
    }

    fun getHaveOpenedSettingsScreen(): Boolean {
        return getSharedPrefs().getBoolean(HAVE_OPENED_SETTINGS_SCREEN, false)
    }

    fun setHaveOpenedSettingsScreen(haveOpened: Boolean) {
        Log.d(TAG, "have opened settings menu = $haveOpened")
        with(getSharedPrefs().edit()) {
            putBoolean(HAVE_OPENED_SETTINGS_SCREEN, haveOpened)
            apply()
        }
    }

    fun getIsOneHandedMode(): Boolean {
        return getSharedPrefs().getBoolean(IS_ONE_HANDED_MODE, false)
    }

    fun setIsOneHandedMode(isOneHandedMode: Boolean) {
        Log.d(TAG, "is single hand mode = $isOneHandedMode")
        with(getSharedPrefs().edit()) {
            putBoolean(IS_ONE_HANDED_MODE, isOneHandedMode)
            apply()
        }
    }

    fun getIsDynamicColor(): Boolean {
        return getSharedPrefs().getBoolean(IS_DYNAMIC_COLOR, true)
    }

    fun setIsDynamicColor(isDynamicColor: Boolean) {
        Log.d(TAG, "is dynamic color = $isDynamicColor")
        with(getSharedPrefs().edit()) {
            putBoolean(IS_DYNAMIC_COLOR, isDynamicColor)
            apply()
        }
    }

    fun getIsSliderIncrementFivePercent(): Boolean {
        return getSharedPrefs().getBoolean(SLIDER_INCREMENT_5_PERCENT, false)
    }

    fun setIsSliderIncrementFivePercent(incrementFivePercent: Boolean) {
        Log.d(TAG, "is slider increment 5% = $incrementFivePercent")
        with(getSharedPrefs().edit()) {
            putBoolean(SLIDER_INCREMENT_5_PERCENT, incrementFivePercent)
            apply()
        }
    }

    fun getCustomVolume(): Int {
        return getSharedPrefs().getInt(CUSTOM_VOLUME, Int.MIN_VALUE)
    }

    fun setCustomVolume(customVolume: Int) {
        Log.d(TAG, "custom volume = $customVolume")
        with(getSharedPrefs().edit()) {
            putInt(CUSTOM_VOLUME, customVolume)
            apply()
        }
    }

    fun getCustomVolumeOptionLabel(): String? {
        return getSharedPrefs().getString(VOLUME_OPTION_LABEL, "")
    }

    fun setCustomVolumeOptionLabel(customOptionLabel: String) {
        Log.d(TAG, "custom volume option label = $customOptionLabel")
        with(getSharedPrefs().edit()) {
            putString(VOLUME_OPTION_LABEL, customOptionLabel)
            apply()
        }
    }

    fun getLuckySpinningWheelItems(): List<String>? {
        val itemsJson = getSharedPrefs().getString(LUCKY_SPINNING_WHEEL_ITEMS, null) ?: return null
        return try {
            Gson().fromJson(itemsJson, object : TypeToken<List<String>>() {}.type)
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "Error parsing spinning wheel items JSON", e)
            null
        }
    }

    fun setLuckySpinningWheelItems(items: List<String>) {
        val itemsJson = Gson().toJson(items)
        Log.d(TAG, "lucky spinning wheel items = $itemsJson")
        with(getSharedPrefs().edit()) {
            putString(LUCKY_SPINNING_WHEEL_ITEMS, itemsJson)
            apply()
        }
    }

    fun clear() {
        Log.d(TAG, "erase all app data")
        with(getSharedPrefs().edit()) {
            clear()
            apply()
        }
    }
}