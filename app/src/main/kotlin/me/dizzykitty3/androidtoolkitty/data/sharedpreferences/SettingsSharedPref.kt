package me.dizzykitty3.androidtoolkitty.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext
import timber.log.Timber

@Serializable
data class WheelOfFortuneItems(val items: List<String>)

object SettingsSharedPref {
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
    private const val USERNAME = "username"

    private const val LAST_TIME_SELECTED_PLATFORM_INDEX = "last_time_selected_platform_index"
    private const val CUSTOM_VOLUME = "custom_volume"
    private const val VOLUME_OPTION_LABEL = "volume_option_label"
    private const val WHEEL_OF_FORTUNE_ITEMS = "wheel_of_fortune_items"

    private const val TOKEN = "token"

    private val sharedPrefs: SharedPreferences
        get() = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private inline fun <reified T> getPreference(key: String, defaultValue: T): T {
        return when (T::class) {
            Boolean::class -> sharedPrefs.getBoolean(key, defaultValue as Boolean) as T
            Int::class -> sharedPrefs.getInt(key, defaultValue as Int) as T
            String::class -> sharedPrefs.getString(key, defaultValue as String) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    private inline fun <reified T> setPreference(key: String, value: T) {
        with(sharedPrefs.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }

    fun removePreference(key: String) {
        with(sharedPrefs.edit()) {
            if (sharedPrefs.contains(key)) {
                remove(key)
                apply()
                Timber.d("Preference key '$key' removed successfully.")
            } else {
                Timber.d("Preference key '$key' does not exist and cannot be removed.")
            }
        }
    }

    fun exportSettingsToJson(): String {
        val keys = sharedPrefs.all.keys.filter { it != TOKEN }  // Exclude the token
        val settingsMap: Map<String, Any?> = keys.associateWith {
            sharedPrefs.all[it] ?: throw IllegalStateException("Unexpected null value at $it")
        }

        val serializableMap = mutableMapOf<String, String>()
        settingsMap.forEach { (key, value) ->
            when (value) {
                is String -> serializableMap[key] = value
                is Int -> serializableMap[key] = value.toString()
                is Boolean -> serializableMap[key] = value.toString()
                else -> throw IllegalArgumentException("Unsupported type for $key")
            }
        }

        return Json.encodeToString(serializableMap)
    }

    fun importSettingsFromJson(jsonString: String) {
        // 假设所有值在 JSON 中都是作为字符串存储的
        val settingsMap: Map<String, String> = Json.decodeFromString(jsonString)
        settingsMap.forEach { (key, value) ->
            // 假设你可以从键值推断出数据类型或在值中存储了类型信息
            when {
                value.toBooleanCustom() != null -> setPreference(key, value.toBoolean())
                value.toIntOrNull() != null -> setPreference(key, value.toInt())
                else -> setPreference(key, value)
            }
        }
    }

    fun clearSettings() {
        val token = getToken()
        sharedPrefs.edit().clear().apply()
        setToken(token)  // Restore token
    }

    private fun String.toBooleanCustom(): Boolean? = when {
        this.equals("true", ignoreCase = true) -> true
        this.equals("false", ignoreCase = true) -> false
        else -> null
    }

    var autoClearClipboard: Boolean
        get() = getPreference(AUTO_CLEAR_CLIPBOARD, false)
        set(value) = setPreference(AUTO_CLEAR_CLIPBOARD, value)

    var sliderIncrement5Percent: Boolean
        get() = getPreference(SLIDER_INCREMENT_5_PERCENT, false)
        set(value) = setPreference(SLIDER_INCREMENT_5_PERCENT, value)

    var dynamicColor: Boolean
        get() = getPreference(DYNAMIC_COLOR, true)
        set(value) = setPreference(DYNAMIC_COLOR, value)

    var oneHandedMode: Boolean
        get() = getPreference(ONE_HANDED_MODE, false)
        set(value) = setPreference(ONE_HANDED_MODE, value)

    var haveOpenedSettingsScreen: Boolean
        get() = getPreference(HAVE_OPENED_SETTINGS_SCREEN, false)
        set(value) = setPreference(HAVE_OPENED_SETTINGS_SCREEN, value)

    var usingCustomVolumeOptionLabel: Boolean
        get() = getPreference(USING_CUSTOM_VOLUME_OPTION_LABEL, false)
        set(value) = setPreference(USING_CUSTOM_VOLUME_OPTION_LABEL, value)

    var debuggingOptions: Boolean
        get() = getPreference(DEBUGGING_OPTIONS, false)
        set(value) = setPreference(DEBUGGING_OPTIONS, value)

    var webpageCardShowMore: Boolean
        get() = getPreference(WEBPAGE_CARD_SHOW_MORE, false)
        set(value) = setPreference(WEBPAGE_CARD_SHOW_MORE, value)

    var collapseKeyboard: Boolean
        get() = getPreference(COLLAPSE_KEYBOARD, true)
        set(value) = setPreference(COLLAPSE_KEYBOARD, value)

    var showDivider: Boolean
        get() = getPreference(SHOW_DIVIDER, true)
        set(value) = setPreference(SHOW_DIVIDER, value)

    var showSnackbar: Boolean
        get() = getPreference(SHOW_SNACKBAR_BEFORE_APPLY_CHANGES, true)
        set(value) = setPreference(SHOW_SNACKBAR_BEFORE_APPLY_CHANGES, value)

    var showEditVolumeOption: Boolean
        get() = getPreference(SHOW_EDIT_VOLUME_OPTION, true)
        set(value) = setPreference(SHOW_EDIT_VOLUME_OPTION, value)

    var autoSetMediaVolume: Int
        get() = getPreference(AUTO_SET_MEDIA_VOLUME, -1)
        set(value) = setPreference(AUTO_SET_MEDIA_VOLUME, value)

    var haveTappedAddButton: Boolean
        get() = getPreference(HAVE_TAPPED_ADD_BUTTON, false)
        set(value) = setPreference(HAVE_TAPPED_ADD_BUTTON, value)

    var uiTesting: Boolean
        get() = getPreference(UI_TESTING, false)
        set(value) = setPreference(UI_TESTING, value)

    var lastTimeSelectedSocialPlatform: Int
        get() = getPreference(LAST_TIME_SELECTED_PLATFORM_INDEX, 0)
        set(value) = setPreference(LAST_TIME_SELECTED_PLATFORM_INDEX, value)

    var customVolume: Int
        get() = getPreference(CUSTOM_VOLUME, Int.MIN_VALUE)
        set(value) = setPreference(CUSTOM_VOLUME, value)

    var customVolumeOptionLabel: String?
        get() = getPreference(VOLUME_OPTION_LABEL, "")
        set(value) = setPreference(VOLUME_OPTION_LABEL, value)

    var username: String?
        get() = getPreference(USERNAME, "")
        set(value) = setPreference(USERNAME, value)

    fun getWheelOfFortuneItems(): List<String>? {
        val itemsJson = getPreference(WHEEL_OF_FORTUNE_ITEMS, "")
        return itemsJson.let {
            try {
                val items: WheelOfFortuneItems = Json.decodeFromString(it)
                items.items
            } catch (e: Exception) {
                null
            }
        }
    }

    fun setWheelOfFortuneItems(items: List<String>) {
        val itemsJson = Json.encodeToString(WheelOfFortuneItems(items))
        Timber.d("wheel of fortune items = $itemsJson")
        setPreference(WHEEL_OF_FORTUNE_ITEMS, itemsJson)
    }

    fun getCardShowedState(card: String): Boolean {
        return sharedPrefs.getBoolean(card, true)
    }

    fun saveCardShowedState(card: String, isShowed: Boolean) {
        Timber.d("$card is showed = $isShowed")
        with(sharedPrefs.edit()) {
            putBoolean(card, isShowed)
            apply()
        }
    }

    fun getToken(): String = getPreference(TOKEN, "")

    fun setToken(token: String) = setPreference(TOKEN, token)


}