package me.dizzykitty3.androidtoolkitty.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

object SettingsSharedPref {
    private const val TAG = "SettingsSharedPref"
    private const val PREF_NAME = "Settings"

    private const val IS_AUTO_CLEAR_CLIPBOARD = "is_auto_clear_clipboard"
    private const val IS_SLIDER_INCREMENT_5_PERCENT = "is_slider_increment_5_percent"
    private const val IS_DYNAMIC_COLOR = "is_dynamic_color"
    private const val IS_ONE_HANDED_MODE = "is_one_handed_mode"
    private const val HAVE_OPENED_SETTINGS_SCREEN = "have_opened_settings_screen"
    private const val USING_CUSTOM_VOLUME_OPTION_LABEL = "using_custom_volume_option_label"

    private const val LAST_TIME_SELECTED_PLATFORM_INDEX = "last_time_selected_platform_index"
    private const val CUSTOM_VOLUME = "custom_volume"
    private const val VOLUME_OPTION_LABEL = "volume_option_label"
    private const val LUCKY_SPINNING_WHEEL_ITEMS = "lucky_spinning_wheel_items"

    private val sharedPrefs: SharedPreferences
        get() = app.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var autoClearClipboard: Boolean
        get() = sharedPrefs.getBoolean(IS_AUTO_CLEAR_CLIPBOARD, false)
        set(value) {
            Log.d(TAG, "is auto clear clipboard = $value")
            with(sharedPrefs.edit()) {
                putBoolean(IS_AUTO_CLEAR_CLIPBOARD, value)
                apply()
            }
        }

    var sliderIncrement5Percent: Boolean
        get() = sharedPrefs.getBoolean(IS_SLIDER_INCREMENT_5_PERCENT, false)
        set(value) {
            Log.d(TAG, "is slider increment 5% = $value")
            with(sharedPrefs.edit()) {
                putBoolean(IS_SLIDER_INCREMENT_5_PERCENT, value)
                apply()
            }
        }

    var dynamicColor: Boolean
        get() = sharedPrefs.getBoolean(IS_DYNAMIC_COLOR, true)
        set(value) {
            Log.d(TAG, "is dynamic color = $value")
            with(sharedPrefs.edit()) {
                putBoolean(IS_DYNAMIC_COLOR, value)
                apply()
            }
        }

    var oneHandedMode: Boolean
        get() = sharedPrefs.getBoolean(IS_ONE_HANDED_MODE, false)
        set(value) {
            Log.d(TAG, "is one-handed mode = $value")
            with(sharedPrefs.edit()) {
                putBoolean(IS_ONE_HANDED_MODE, value)
                apply()
            }
        }

    var openedSettingsScreen: Boolean
        get() = sharedPrefs.getBoolean(HAVE_OPENED_SETTINGS_SCREEN, false)
        set(value) {
            Log.d(TAG, "have opened settings menu = $value")
            with(sharedPrefs.edit()) {
                putBoolean(HAVE_OPENED_SETTINGS_SCREEN, value)
                apply()
            }
        }

    var haveCustomLabel: Boolean
        get() = sharedPrefs.getBoolean(USING_CUSTOM_VOLUME_OPTION_LABEL, false)
        set(value) {
            Log.d(TAG, "using custom volume option label = $value")
            with(sharedPrefs.edit()) {
                putBoolean(USING_CUSTOM_VOLUME_OPTION_LABEL, value)
                apply()
            }
        }

    fun getCardShowedState(cardId: String): Boolean {
        return sharedPrefs.getBoolean(cardId, true)
    }

    fun saveCardShowedState(cardId: String, isShowed: Boolean) {
        Log.d(TAG, "$cardId is showed = $isShowed")
        with(sharedPrefs.edit()) {
            putBoolean(cardId, isShowed)
            apply()
        }
    }

    fun getLastTimeSelectedSocialPlatform(): Int {
        return sharedPrefs.getInt(LAST_TIME_SELECTED_PLATFORM_INDEX, 0)
    }

    fun saveSelectedSocialPlatform(lastTimePlatformIndex: Int) {
        Log.d(TAG, "last time platform index = $lastTimePlatformIndex")
        with(sharedPrefs.edit()) {
            putInt(LAST_TIME_SELECTED_PLATFORM_INDEX, lastTimePlatformIndex)
            apply()
        }
    }

    fun getCustomVolume(): Int {
        return sharedPrefs.getInt(CUSTOM_VOLUME, Int.MIN_VALUE)
    }

    fun setCustomVolume(customVolume: Int) {
        Log.d(TAG, "custom volume = $customVolume")
        with(sharedPrefs.edit()) {
            putInt(CUSTOM_VOLUME, customVolume)
            apply()
        }
    }

    fun getCustomVolumeOptionLabel(): String? {
        return sharedPrefs.getString(VOLUME_OPTION_LABEL, "")
    }

    fun setCustomVolumeOptionLabel(customOptionLabel: String) {
        Log.d(TAG, "custom volume option label = $customOptionLabel")
        with(sharedPrefs.edit()) {
            putString(VOLUME_OPTION_LABEL, customOptionLabel)
            apply()
        }
    }

    fun getLuckySpinningWheelItems(): List<String>? {
        val itemsJson = sharedPrefs.getString(LUCKY_SPINNING_WHEEL_ITEMS, null) ?: return null
        return try {
            val itemsArray: Array<String> = Gson().fromJson(itemsJson, Array<String>::class.java)
            itemsArray.toList()
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "Error parsing spinning wheel items JSON", e)
            null
        }
    }

    fun setLuckySpinningWheelItems(items: List<String>) {
        val itemsJson = Gson().toJson(items)
        Log.d(TAG, "lucky spinning wheel items = $itemsJson")
        with(sharedPrefs.edit()) {
            putString(LUCKY_SPINNING_WHEEL_ITEMS, itemsJson)
            apply()
        }
    }

    fun clear() {
        Log.d(TAG, "erase all app data")
        with(sharedPrefs.edit()) {
            clear()
            apply()
        }
    }
}