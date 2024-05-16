package me.dizzykitty3.androidtoolkitty.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext

@Serializable
data class WheelOfFortuneItems(val items: List<String>)

object SettingsSharedPref {
    private const val TAG = "SettingsSharedPref"
    private const val PREF_NAME = "Settings"

    private const val AUTO_CLEAR_CLIPBOARD = "auto_clear_clipboard"
    private const val SLIDER_INCREMENT_5_PERCENT = "slider_increment_5_percent"
    private const val DYNAMIC_COLOR = "dynamic_color"
    private const val ONE_HANDED_MODE = "one_handed_mode"
    private const val HAVE_OPENED_SETTINGS_SCREEN = "have_opened_settings_screen"
    private const val USING_CUSTOM_VOLUME_OPTION_LABEL = "using_custom_volume_option_label"
    private const val DEBUGGING_OPTIONS = "debugging_options"
    private const val WEBPAGE_CARD_SHOW_MORE = "webpage_card_show_more"
    private const val COLLAPSE_KEYBOARD = "collapse_keyboard"
    private const val SHOW_DIVIDER = "show_divider"
    private const val SHOW_SNACKBAR_BEFORE_APPLY_CHANGES = "show_snackbar_to_confirm"
    private const val SHOW_EDIT_VOLUME_OPTION = "show_edit_volume_option"
    private const val AUTO_SET_MEDIA_VOLUME = "auto_set_media_volume"
    private const val HAVE_TAPPED_ADD_BUTTON = "have_tapped_add_button"
    private const val UI_TESTING = "ui_testing"

    private const val LAST_TIME_SELECTED_PLATFORM_INDEX = "last_time_selected_platform_index"
    private const val CUSTOM_VOLUME = "custom_volume"
    private const val VOLUME_OPTION_LABEL = "volume_option_label"
    private const val WHEEL_OF_FORTUNE_ITEMS = "wheel_of_fortune_items"

    private val sharedPrefs: SharedPreferences
        get() = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var autoClearClipboard: Boolean
        get() = sharedPrefs.getBoolean(AUTO_CLEAR_CLIPBOARD, false)
        set(value) {
            Log.d(TAG, "auto clear clipboard = $value")
            with(sharedPrefs.edit()) {
                putBoolean(AUTO_CLEAR_CLIPBOARD, value)
                apply()
            }
        }

    var sliderIncrement5Percent: Boolean
        get() = sharedPrefs.getBoolean(SLIDER_INCREMENT_5_PERCENT, false)
        set(value) {
            Log.d(TAG, "slider increment 5% = $value")
            with(sharedPrefs.edit()) {
                putBoolean(SLIDER_INCREMENT_5_PERCENT, value)
                apply()
            }
        }

    var dynamicColor: Boolean
        get() = sharedPrefs.getBoolean(DYNAMIC_COLOR, true)
        set(value) {
            Log.d(TAG, "dynamic color = $value")
            with(sharedPrefs.edit()) {
                putBoolean(DYNAMIC_COLOR, value)
                apply()
            }
        }

    var oneHandedMode: Boolean
        get() = sharedPrefs.getBoolean(ONE_HANDED_MODE, false)
        set(value) {
            Log.d(TAG, "one-handed mode = $value")
            with(sharedPrefs.edit()) {
                putBoolean(ONE_HANDED_MODE, value)
                apply()
            }
        }

    var haveOpenedSettingsScreen: Boolean
        get() = sharedPrefs.getBoolean(HAVE_OPENED_SETTINGS_SCREEN, false)
        set(value) {
            Log.d(TAG, "have opened settings menu = $value")
            with(sharedPrefs.edit()) {
                putBoolean(HAVE_OPENED_SETTINGS_SCREEN, value)
                apply()
            }
        }

    var usingCustomVolumeOptionLabel: Boolean
        get() = sharedPrefs.getBoolean(USING_CUSTOM_VOLUME_OPTION_LABEL, false)
        set(value) {
            Log.d(TAG, "using custom volume option label = $value")
            with(sharedPrefs.edit()) {
                putBoolean(USING_CUSTOM_VOLUME_OPTION_LABEL, value)
                apply()
            }
        }

    var debuggingOptions: Boolean
        get() = sharedPrefs.getBoolean(DEBUGGING_OPTIONS, false)
        set(value) {
            Log.d(TAG, "debugging options = $value")
            with(sharedPrefs.edit()) {
                putBoolean(DEBUGGING_OPTIONS, value)
                apply()
            }
        }

    var webpageCardShowMore: Boolean
        get() = sharedPrefs.getBoolean(WEBPAGE_CARD_SHOW_MORE, false)
        set(value) {
            Log.d(TAG, "webpage card show more = $value")
            with(sharedPrefs.edit()) {
                putBoolean(WEBPAGE_CARD_SHOW_MORE, value)
                apply()
            }
        }

    var collapseKeyboard: Boolean
        get() = sharedPrefs.getBoolean(COLLAPSE_KEYBOARD, true)
        set(value) {
            Log.d(TAG, "collapse keyboard = $value")
            with(sharedPrefs.edit()) {
                putBoolean(COLLAPSE_KEYBOARD, value)
                apply()
            }
        }

    var showDivider: Boolean
        get() = sharedPrefs.getBoolean(SHOW_DIVIDER, true)
        set(value) {
            Log.d(TAG, "show divider = $value")
            with(sharedPrefs.edit()) {
                putBoolean(SHOW_DIVIDER, value)
                apply()
            }
        }

    var showSnackbar: Boolean
        get() = sharedPrefs.getBoolean(SHOW_SNACKBAR_BEFORE_APPLY_CHANGES, true)
        set(value) {
            Log.d(TAG, "show snackbar before apply changes = $value")
            with(sharedPrefs.edit()) {
                putBoolean(SHOW_SNACKBAR_BEFORE_APPLY_CHANGES, value)
                apply()
            }
        }

    var showEditVolumeOption: Boolean
        get() = sharedPrefs.getBoolean(SHOW_EDIT_VOLUME_OPTION, true)
        set(value) {
            Log.d(TAG, "show edit volume option = $value")
            with(sharedPrefs.edit()) {
                putBoolean(SHOW_EDIT_VOLUME_OPTION, value)
                apply()
            }
        }

    var autoSetMediaVolume: Int
        get() = sharedPrefs.getInt(AUTO_SET_MEDIA_VOLUME, -1)
        set(value) {
            Log.d(TAG, "auto set media volume = $value")
            with(sharedPrefs.edit()) {
                putInt(AUTO_SET_MEDIA_VOLUME, value)
                apply()
            }
        }

    var haveTappedAddButton: Boolean
        get() = sharedPrefs.getBoolean(HAVE_TAPPED_ADD_BUTTON, false)
        set(value) {
            Log.d(TAG, "have tapped add button = $value")
            with(sharedPrefs.edit()) {
                putBoolean(HAVE_TAPPED_ADD_BUTTON, value)
                apply()
            }
        }

    var uiTesting: Boolean
        get() = sharedPrefs.getBoolean(UI_TESTING, false)
        set(value) {
            Log.d(TAG, "UI testing = $value")
            with(sharedPrefs.edit()) {
                putBoolean(UI_TESTING, value)
                apply()
            }
        }

    fun getCardShowedState(card: String): Boolean {
        return sharedPrefs.getBoolean(card, true)
    }

    fun saveCardShowedState(card: String, isShowed: Boolean) {
        Log.d(TAG, "$card is showed = $isShowed")
        with(sharedPrefs.edit()) {
            putBoolean(card, isShowed)
            apply()
        }
    }

    var lastTimeSelectedSocialPlatform: Int
        get() = sharedPrefs.getInt(LAST_TIME_SELECTED_PLATFORM_INDEX, 0)
        set(value) {
            Log.d(TAG, "last time platform index = $value")
            with(sharedPrefs.edit()) {
                putInt(LAST_TIME_SELECTED_PLATFORM_INDEX, value)
                apply()
            }
        }

    var customVolume: Int
        get() = sharedPrefs.getInt(CUSTOM_VOLUME, Int.MIN_VALUE)
        set(value) {
            Log.d(TAG, "custom volume = $value")
            with(sharedPrefs.edit()) {
                putInt(CUSTOM_VOLUME, value)
                apply()
            }
        }

    var customVolumeOptionLabel: String?
        get() = sharedPrefs.getString(VOLUME_OPTION_LABEL, "")
        set(value) {
            Log.d(TAG, "custom volume option label = $value")
            with(sharedPrefs.edit()) {
                putString(VOLUME_OPTION_LABEL, value)
                apply()
            }
        }

    fun getWheelOfFortuneItems(): List<String>? {
        val itemsJson = sharedPrefs.getString(WHEEL_OF_FORTUNE_ITEMS, null) ?: return null
        return try {
            val items: WheelOfFortuneItems = Json.decodeFromString(itemsJson)
            items.items
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing wheel of fortune items JSON", e)
            null
        }
    }

    fun setWheelOfFortuneItems(items: List<String>) {
        val itemsJson = Json.encodeToString(WheelOfFortuneItems(items))
        Log.d(TAG, "wheel of fortune items = $itemsJson")
        with(sharedPrefs.edit()) {
            putString(WHEEL_OF_FORTUNE_ITEMS, itemsJson)
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