package me.dizzykitty3.androidtoolkitty.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import timber.log.Timber

@Serializable
data class WheelOfFortuneItems(val items: List<String>)

object SettingsSharedPref {
    private const val PREF_NAME = "Settings"

    // Booleans
    private const val USING_CUSTOM_VOLUME_OPTION_LABEL = "using_custom_volume_option_label"
    private const val AUTO_SET_MEDIA_VOLUME = "auto_set_media_volume"
    private const val HAVE_TAPPED_ADD_BUTTON = "have_tapped_add_button"

    private const val LAST_TIME_SELECTED_PLATFORM_INDEX = "last_time_selected_platform_index"
    private const val CUSTOM_VOLUME = "custom_volume"
    private const val VOLUME_OPTION_LABEL = "volume_option_label"
    private const val SAVED_LATITUDE = "saved_latitude"
    private const val SAVED_LONGITUDE = "saved_longitude"

    private const val WHEEL_OF_FORTUNE_ITEMS = "wheel_of_fortune_items"

    private val sharedPrefs: SharedPreferences
        get() = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private inline fun <reified T> getPreference(key: String, defaultValue: T): T {
        return when (T::class) {
            Boolean::class -> sharedPrefs.getBoolean(key, defaultValue as Boolean) as T
            Int::class -> sharedPrefs.getInt(key, defaultValue as Int) as T
            String::class -> sharedPrefs.getString(key, defaultValue as String) as T
            Float::class -> sharedPrefs.getFloat(key, defaultValue as Float) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    private inline fun <reified T> setPreference(key: String, value: T) {
        Timber.d("set preference: $key = $value")
        with(sharedPrefs.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Float -> putFloat(key, value)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }

    var usingCustomVolumeOptionLabel: Boolean
        get() = getPreference(USING_CUSTOM_VOLUME_OPTION_LABEL, false)
        set(value) = setPreference(USING_CUSTOM_VOLUME_OPTION_LABEL, value)

    var autoSetMediaVolume: Int
        get() = getPreference(AUTO_SET_MEDIA_VOLUME, -1)
        set(value) = setPreference(AUTO_SET_MEDIA_VOLUME, value)

    val enabledAutoSetMediaVolume: Boolean
        get() = autoSetMediaVolume != -1

    var haveTappedAddButton: Boolean
        get() = getPreference(HAVE_TAPPED_ADD_BUTTON, false)
        set(value) = setPreference(HAVE_TAPPED_ADD_BUTTON, value)

    var lastTimeSelectedSocialPlatform: Int
        get() = getPreference(LAST_TIME_SELECTED_PLATFORM_INDEX, 0)
        set(value) = setPreference(LAST_TIME_SELECTED_PLATFORM_INDEX, value)

    var customVolume: Int
        get() = getPreference(CUSTOM_VOLUME, Int.MIN_VALUE)
        set(value) = setPreference(CUSTOM_VOLUME, value)

    val addedCustomVolume: Boolean
        get() = customVolume > 0

    var customVolumeOptionLabel: String?
        get() = getPreference(VOLUME_OPTION_LABEL, "")
        set(value) = setPreference(VOLUME_OPTION_LABEL, value)

    fun getWheelOfFortuneItems(): List<String>? {
        val itemsJson = sharedPrefs.getString(WHEEL_OF_FORTUNE_ITEMS, null) ?: return null
        return try {
            val items: WheelOfFortuneItems = Json.decodeFromString(itemsJson)
            items.items
        } catch (e: Exception) {
            Timber.e(e)
            null
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

    var savedLatitude: Float
        get() = getPreference(SAVED_LATITUDE, 0f)
        set(value) = setPreference(SAVED_LATITUDE, value)

    var savedLongitude: Float
        get() = getPreference(SAVED_LONGITUDE, 0f)
        set(value) = setPreference(SAVED_LONGITUDE, value)

    var testLayout: Boolean
        get() = getPreference("test_layout", false)
        set(value) = setPreference("test_layout", value)
}